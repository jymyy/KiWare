package modelo;

import java.util.List;

public class Almacen {
	private List<ProductoLacteo> listPL;
	private List<MateriaPrima> listMP;

	public Almacen(List<ProductoLacteo> listPL, List<MateriaPrima> listMP) {
		super();
		this.listPL = listPL;
		this.listMP = listMP;
	}

	public List<MateriaPrima> getListMP() {
		return listMP;
	}

	public void setListMP(List<MateriaPrima> listMP) {
		this.listMP = listMP;
	}

	public List<ProductoLacteo> getListPL() {
		return listPL;
	}

	public void setListPL(List<ProductoLacteo> listPL) {
		this.listPL = listPL;
	}

}
