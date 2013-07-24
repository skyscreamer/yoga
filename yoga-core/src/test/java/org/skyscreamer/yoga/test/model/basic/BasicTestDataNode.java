package org.skyscreamer.yoga.test.model.basic;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

@URITemplate("/basic-node/{id}")
public class BasicTestDataNode
{

    String id;
    BasicTestDataLeaf leaf;

    public BasicTestDataLeaf getLeaf()
    {
        return leaf;
    }

    @Core
    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public void setLeaf( BasicTestDataLeaf leaf )
    {
        this.leaf = leaf;
    }

}
