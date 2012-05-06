package org.skyscreamer.yoga.test.data;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

@URITemplate("/basic-node/{id}")
public class BasicTestDataNode
{

    String id;
    BasicTestDataNode node;

    public BasicTestDataNode getNode()
    {
        return node;
    }

    @Core
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setNode(BasicTestDataNode node)
    {
        this.node = node;
    }

}
