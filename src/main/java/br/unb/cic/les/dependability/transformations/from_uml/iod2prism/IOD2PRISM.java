package br.unb.cic.les.dependability.transformations.from_uml.iod2prism;

import br.unb.cic.les.dependability.transformations.from_uml.AbstractTransformation;

public class IOD2PRISM extends AbstractTransformation {
	
	public IOD2PRISM (String umlPath, String outputFolderPath) {
		try {
			initialize(umlPath, outputFolderPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void generate() {
		for (int i = 0; i < model.eContents().size(); i++) {
			System.out.println(model.eContents().get(i).toString());
		}
	}
	
}
