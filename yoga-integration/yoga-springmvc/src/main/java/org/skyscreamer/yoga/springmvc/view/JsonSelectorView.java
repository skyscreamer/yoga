package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.mapper.MapHierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverserContext;
import org.skyscreamer.yoga.selector.Selector;

public class JsonSelectorView extends AbstractYogaView {
	@Override
	public void render(OutputStream outputStream, Selector selector, Object value, HttpServletResponse response) throws IOException {
		Object viewData;
		ResultTraverserContext context = new ResultTraverserContext(getHrefSuffix(), response);
		if (value instanceof Iterable<?>) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Object instance : (Iterable<?>) value) {
				list.add(getSingleResult(instance, selector, context));
			}
			viewData = list;
		} else {
			viewData = getSingleResult(value, selector, context);
		}
		getObjectMapper().writeValue(outputStream, viewData);
	}

	protected ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	protected Map<String, Object> getSingleResult(Object value, Selector selector, ResultTraverserContext context) {
		MapHierarchicalModel model = new MapHierarchicalModel();
		resultTraverser.traverse(value, selector, model, context);
		return model.getObjectTree();
	}

	@Override
	public String getContentType() {
		return "application/json";
	}

	@Override
	public String getHrefSuffix() {
		return "json";
	}
}
