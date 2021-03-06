package cd4017be.rs_ctr.circuit.gates;

import org.objectweb.asm.Type;
import cd4017be.rs_ctr.circuit.editor.GeneratedType;
import cd4017be.rscpl.compile.Node;
import cd4017be.rscpl.editor.InvalidSchematicException;
import cd4017be.rscpl.graph.IReadVar;
import cd4017be.rscpl.graph.IWriteVar;

/** @author CD4017BE */
public class ReadWriteVar extends End
implements IReadVar, IWriteVar {

	public ReadWriteVar(GeneratedType type, int index) {
		super(type, index);
	}

	@Override
	public String name() {
		return label;
	}

	@Override
	public Type type() {
		return type.getOutType(0);
	}

	@Override
	public Object getValue() {
		return getParam(0);
	}

	@Override
	public void link(IReadVar read) throws InvalidSchematicException {
		if (read != null && !read.type().equals(this.type()))
			throw new InvalidSchematicException(InvalidSchematicException.VAR_TYPE_CONFLICT, this, 0);
	}

	@Override
	public Node result() {
		return null;
	}

	@Override
	protected boolean isIndependent(int out) {
		return true;
	}

}
