package br.com.medina.escolaconectada.models.enums;

public enum MateriaEnum {
	
	MATEMATICA(0,"matemática"), PORTUGUES(1, "português");

	private int id;
	private String description;

	private MateriaEnum(int id,String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static MateriaEnum toEnum(Integer id) {

		if (id == null) {
			return null;
		}

		for (MateriaEnum x : MateriaEnum.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id invalido: ");
	}

	
}
