package br.com.medina.escolaconectada.models.enums;

public enum BimestreEnum {
	
	PRIMEIRO(1,"Primeiro bimestre"),
	SEGUNDO(2, "Segundo bimestre"),
	TERCEIRO(3,"Terceiro bimestre"),
	QUARTO(4, "Quarto bimestre");

	private int id;
	private String description;

	private BimestreEnum(int id,String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static BimestreEnum toEnum(Integer id) {

		if (id == null) {
			return null;
		}

		for (BimestreEnum x : BimestreEnum.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id invalido: ");
	}

}
