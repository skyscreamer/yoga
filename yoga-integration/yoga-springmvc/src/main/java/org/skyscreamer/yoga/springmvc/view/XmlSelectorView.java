package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverserContext;
import org.skyscreamer.yoga.mapper.XmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

public class XmlSelectorView extends AbstractYogaView {
	@Override
	public void render(OutputStream outputStream, Selector selector, Object value, HttpServletResponse response) throws IOException {
		ResultTraverserContext context = new ResultTraverserContext(getHrefSuffix(), response);

		DOMDocument domDocument = new DOMDocument();
		if (value instanceof Iterable) {
			DOMElement root = createDocument(domDocument, "result");
			HierarchicalModel model = new XmlHierarchyModel(root);
			for (Object child : (Iterable<?>) value) {
				resultTraverser.traverse(child, selector, model, context);
			}
		} else {
			String name = NameUtil.getName(resultTraverser.findClass(value));
			DOMElement root = createDocument(domDocument, name);
			resultTraverser.traverse(value, selector, new XmlHierarchyModel(root), context);
		}
		write(outputStream, domDocument);
	}

	public DOMElement createDocument(DOMDocument domDocument, String name) {
		DOMElement root = new DOMElement(name);
		domDocument.setRootElement(root);
		return root;
	}

	@Override
	public String getContentType() {
		return "application/xml";
	}

	@Override
	public String getHrefSuffix() {
		return "xml";
	}
}
