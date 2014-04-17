package ro.pub.cs.elf.crespo.gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import ro.pub.cs.elf.crespo.app.ICommand;
import ro.pub.cs.elf.crespo.mediator.Mediator;

/**
 * Parametrized list {@link JList}
 * @param <E> List type
 */
public abstract class AbstractList<E> extends JList<E> implements ICommand {

	private static final long serialVersionUID = -5945358337758113651L;
	private final DefaultListModel<E> model;
	protected final Mediator mediator;

	public AbstractList(Mediator mediator) {
		super();
		this.model = new DefaultListModel<E>();
		setModel(this.model);
		this.mediator = mediator;
	}

	/**
	 * add new element new to list
	 * @param e element to add
	 */
	public void addElement(E e) {
		this.model.addElement(e);
	}

	/**
	 * remove an element from list
	 * @param e element to remove
	 */
	public void removeElement(E e) {
		this.model.removeElement(e);
	}

	/**
	 * clear list model
	 */
	public void clearList() {
		this.model.clear();
	}
}
