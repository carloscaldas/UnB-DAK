package transformations.from_uml;

import br.unb.cic.les.dependability.transformations.from_uml.iod2prism.IOD2PRISM;

public class IOD2PRISM_Test {

	public static void main(String[] args) {
		String modelPath = "input/aal.xmi";
		String outputFolderPath = "output";
		IOD2PRISM test = new IOD2PRISM(modelPath, outputFolderPath);
		test.generate();
	}

}
