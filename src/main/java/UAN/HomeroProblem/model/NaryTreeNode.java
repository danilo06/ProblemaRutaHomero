package UAN.HomeroProblem.model;

import java.util.ArrayList;

public class NaryTreeNode {
	final String LABEL;
	private final ArrayList<NaryTreeNode> children;

	public NaryTreeNode(String LABEL) {
		this.LABEL = LABEL;
		children = new ArrayList<>();
	}

	private boolean addChild(NaryTreeNode node) {
		return children.add(node);
	}

	public boolean addChild(String label) {
		return addChild(new NaryTreeNode(label));
	}

	public ArrayList<NaryTreeNode> getChildren() {
		return new ArrayList<>(children);
	}

	public NaryTreeNode getChild(int index) {
		if (index < children.size()) {
			return children.get(index);
		}
		return null;
	}

	public String getLABEL() {
		return LABEL;
	}

	public static void print(NaryTreeNode root) {
		System.out.println();
		System.out.println("-- ARBOL --");
		System.out.println();
		printUtil(root, 0);
	}

	private static void printUtil(NaryTreeNode node, int depth) {
		for (int i = 0; i < depth; ++i) {
			System.out.print("   " + i);
		}
		System.out.println(" " + node.LABEL);
		for (NaryTreeNode child : node.getChildren()) {
			printUtil(child, depth + 1);
		}
	}
}