package model;

public abstract class Geometry {
	
	private String name;
	private ObjectType type;
	
	public Geometry(String name, ObjectType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public ObjectType getType() {
		return type;
	}

	// Isso aqui ta errado, é só pra testar uma transformação
	// Deve ser genérico passando uma matriz transformada,
	// não uma operação
	// Validando os links entre controller e view
	// Só um exemplo de uma transformação
	public abstract void moveUp(double factor);
	
}
