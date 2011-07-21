package org.skyscreamer.yoga.mapper;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

/**
 * Created by IntelliJ IDEA. User: corby Date: 4/21/11 Time: 3:07 PM
 */
public class XmlResultMapper
{
   ResultTraverser _resultTraverser = new ResultTraverser();

   public void setResultTraverser(ResultTraverser resultTraverser)
   {
      this._resultTraverser = resultTraverser;
   }

   public DOMDocument populate(Iterable<?> instances, Selector fieldSelector)
   {
      DOMDocument domDocument = new DOMDocument();
      DOMElement root = new DOMElement("result");
      domDocument.setRootElement(root);
      HierarchicalModel model = new XmlHierarchyModel(root);
      for (Object instance : instances)
      {
         _resultTraverser.traverse(instance, fieldSelector, model);
      }
      return domDocument;
   }

   public DOMDocument populate(Object instance, Selector fieldSelector)
   {
      DOMDocument domDocument = new DOMDocument();
      DOMElement root = new DOMElement(NameUtil.getName(_resultTraverser.getClass(instance)));
      domDocument.setRootElement(root);
      _resultTraverser.traverse(instance, fieldSelector, new XmlHierarchyModel(root));
      return domDocument;
   }

   public ResultTraverser getResultTraverser()
   {
      return _resultTraverser;
   }
}
