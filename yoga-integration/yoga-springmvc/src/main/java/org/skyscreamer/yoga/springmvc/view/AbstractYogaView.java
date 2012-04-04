package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.dom.DOMDocument;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;

/**
 * This MessageConvert gets the selector from the request. Children do the
 * interesting output. NOTE: you have to put in a
 * org.skyscreamer.yoga.springmvc.view.RequestHolder in your web.xml file
 * 
 * @author Solomon Duskis
 */
public abstract class AbstractYogaView implements View {
	@Autowired
	protected ResultTraverser resultTraverser;

	@Autowired
	protected SelectorParser _selectorParser;

	public void setResultTraverser(ResultTraverser resultTraverser) {
		this.resultTraverser = resultTraverser;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType(getContentType());
		render(response.getOutputStream(), getSelector(request), model.values().iterator().next(), response);
	}

	protected Selector getSelector(HttpServletRequest request) {
		String selectorString = request.getParameter("selector");
		return _selectorParser.parseSelector(selectorString);
	}

	protected static void write(OutputStream output, DOMDocument domDocument) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(output);
		domDocument.write(out);
		out.flush();
	}

	public abstract void render(OutputStream outputStream, Selector selector, Object value, HttpServletResponse response) throws IOException;

	public abstract String getHrefSuffix();
}
