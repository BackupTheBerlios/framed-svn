/*
 * Created on Oct 20, 2004
 *
 */
package net.addictivesoftware.album.pages;

import org.apache.tapestry.contrib.tree.model.ITreeDataModel;
import org.apache.tapestry.contrib.tree.simple.SimpleTreeDataModel;


/**
 * @author John Ryenolds
 * This is the source of the data for the tree examples
 */
public class StringTreeSource {
	
	ITreeDataModel treeDataModel;
	public ITreeDataModel getTreeDataModel()
	{
		if( treeDataModel == null)
		{
			StringTreeNode node1 = new StringTreeNode("node1");
			StringTreeNode node2 = new StringTreeNode("node2");
			StringTreeNode node2a = new StringTreeNode("node2a");
			node2.insert(node2a);
			node1.insert(node2);
			StringTreeNode node3 = new StringTreeNode("node3");
			StringTreeNode node3a = new StringTreeNode("node3a");
			node3.insert(node3a);
			StringTreeNode node3a1 = new StringTreeNode("node3a1");
			node3a.insert(node3a1);
			node1.insert(node3);
			System.out.println("node1 has " + node1.getChildCount() + " children" );
			treeDataModel = new SimpleTreeDataModel(node1);
		}
		System.out.println("getting TreeDataModel");
		return treeDataModel;
	}

}
