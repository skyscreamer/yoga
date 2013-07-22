package org.skyscreamer.yoga.demo.performance;

import java.io.ByteArrayOutputStream;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.listener.CountLimitRenderingListener;
import org.skyscreamer.yoga.view.AbstractYogaView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class PerfRunner
{
    PlatformTransactionManager txManager;
    GenericDao dao;
    AbstractYogaView view;

    MockHttpServletRequest request = new MockHttpServletRequest();
    {
        request.setParameter("selector", "recommendedAlbums(songs(artist(fans)))");
    }
    MockHttpServletResponse response = new MockHttpServletResponse();
    Runtime r = Runtime.getRuntime();
	private TransactionStatus transaction;
	private User user;

    public long getMem(){
        return r.totalMemory() - r.freeMemory();
    }

    public void init(){
        transaction = txManager
                .getTransaction(new DefaultTransactionDefinition());
        user = dao.find(User.class, 1);
    }

    
    public void end(){
        txManager.commit(transaction);
    }
    protected void render(int renderCount) throws Exception
    {
        r.gc();
        long size = 0;
//        long mem = getMem();
        long start = System.nanoTime();
        for (int i = 0; i < renderCount; i++)
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            view.render(request, response, user, out);
            size += out.size();
//            System.out.println(new String(out.toByteArray()));
        }
        float total = (System.nanoTime() - start) / 1000000;
//        long endMem = getMem();
        System.out.println(String.format("rendering %d took %.2f avg: %.2f, memory: %.2fK size: %d",
                renderCount, total, total / renderCount,  ((float)getMem())/1024, size));
    }

    public static void main(String[] args) throws Exception
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml", "classpath:/applicationContext-test.xml");
        PerfRunner perf = new PerfRunner();
        perf.txManager = context.getBean(PlatformTransactionManager.class);
        perf.dao = context.getBean(GenericDao.class);
        context.getBean(CountLimitRenderingListener.class).setMaxCount(Integer.MAX_VALUE);

        perf.init();

        System.out.println("\n\nTesting default json");
        perf.view = context.getBean("json.view", AbstractYogaView.class);
        test(perf);

        System.out.println("\n\nTesting streaming json");
        perf.view = context.getBean("json.streaming.view", AbstractYogaView.class);
        test(perf);
        
        perf.end();
    }

	private static void test(PerfRunner perf) throws Exception {
		perf.render(1);
        perf.render(2);
        perf.render(2);
        perf.render(2);
        perf.render(2);
        perf.render(10);
        perf.render(10);
        perf.render(10);
        perf.render(2);
        perf.render(2);
	}

}
