package ro.pub.cs.elf.crespo.gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public abstract class AbstractList<E> extends JList<E> {

	private static final long serialVersionUID = -5945358337758113651L;
	private final DefaultListModel<E> model;

	public AbstractList() {
		super();
		this.model = new DefaultListModel<E>();
		setModel(this.model);
	}

	public void addElement(E e) {
		this.model.addElement(e);
	}

	public void removeElement(E e) {
		this.model.removeElement(e);
	}

	public int getListSize() {
		return this.model.getSize();
	}

}