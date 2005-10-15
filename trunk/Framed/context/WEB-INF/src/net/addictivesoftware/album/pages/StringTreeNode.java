/*
 * Created on Oct 20, 2004
 *
 */
package net.addictivesoftware.album.pages;

import org.apache.tapestry.contrib.tree.model.IMutableTreeNode;
import org.apache.tapestry.contrib.tree.simple.TreeNode;

/**
 * @author John Ryenolds
 * This is a simple extension of TreeNode that can be used for
 * trees that display Strings.
 */
public class StringTreeNode extends TreeNode {

	String strValue;
    /**
     */
    public String getValue() {
        return strValue;
    }
	
	public StringTreeNode( String strValue)
	{
		super();
		this.strValue = strValue;
	}

	public StringTreeNode( String strValue, IMutableTreeNode parent)
	{
		super(parent);
		this.strValue = strValue;
	}

	/**
	 *  @see org.apache.tapestry.contrib.tree.simple.SimpleNodeRenderFactory
	 *  SimpleNodeRenderFactory.getRender() returns a  RenderString 
	 *  instanciated by object.toString()
	 * 
	 *  If we want anything other then the serialized object displayed
	 *  we have to overwrite toString()
	 */
	public String toString(){
        return getValue();
    }

    /**
     *  Overwrite hashCode to match getValue().hashCode()
     */
    public int hashCode(){
        return getValue().hashCode();
    }

    /**
     *  Overwrite equals to match getValue().equals()
     */
    public boolean equals(Object objTarget){
        if(objTarget == this)
            return true;
        if(! (objTarget instanceof StringTreeNode))
            return false;
        StringTreeNode objTargetNode = (StringTreeNode)objTarget;
        return this.getValue().equals(objTargetNode.getValue());
    }

}
