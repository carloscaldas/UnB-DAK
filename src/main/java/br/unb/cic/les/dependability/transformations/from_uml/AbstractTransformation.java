/**
 * This module was generated based on Acceleo's APIs.
 * 
 * @author abiliooliveira
 */

package br.unb.cic.les.dependability.transformations.from_uml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceFactoryRegistry;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;

public abstract class AbstractTransformation {
	/** The model on which to iterate for this generation. */
	protected EObject model;
	
	/** The folder that will be considered "root" of the generated files*/
	protected File outputFolder;
	
	/** This will be used to know which resource to <u>not</u> unload from the resourceSet post generation. */
	protected Set<Resource> originalResources = new CompactHashSet<Resource>();
	
	/** Used only to restore the resource factory registry after the generation is done. */
	protected Resource.Factory.Registry resourceFactoryRegistry;
	
	/** People should implement this method to create the generation base on the model property*/
	public abstract void generate();
	
	/** 
	 * This will initialize all required information for this generator
	 * @param model 
	 * 			The element to iterate over
	 * @param outputFolder
	 * 			The folder to output the generation result files
	 */
	public void initialize(EObject model, File outputFolder) throws IOException {
		ResourceSet resourceSet = new AcceleoResourceSetImpl();
		resourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);

		resourceFactoryRegistry = resourceSet.getResourceFactoryRegistry();
		resourceSet.setResourceFactoryRegistry(new AcceleoResourceFactoryRegistry(resourceFactoryRegistry));

		originalResources.addAll(resourceSet.getResources());

		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);


		this.model = model;
		this.outputFolder = outputFolder;
	}
	
	/** 
	 * This will initialize all required information for this generator
	 * @param modelURI 
	 * 			The URI to the element to iterate over
	 * @param outputFolder
	 * 			The folder to output the generation result files
	 */
	public void initialize(URI modelURI, File outputFolder) throws IOException {
		ResourceSet modelResourceSet = new AcceleoResourceSetImpl();
		modelResourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);

		registerResourceFactories(modelResourceSet);
		registerPackages(modelResourceSet);
		
		URI newModelURI = URI.createURI(modelURI.toString(), true);
		model = ModelUtils.load(newModelURI, modelResourceSet);
		this.outputFolder = outputFolder;
	}
	
	public void initialize(String umlPath, String outputFolderPath) throws IOException {
		URL umlURL = this.getClass().getClassLoader().getResource(umlPath);
		URL outputFolderURL = this.getClass().getClassLoader().getResource(outputFolderPath);
		initialize(URI.createFileURI(umlURL.getPath()), new File(outputFolderURL.getPath()));
	}
	
	public void registerResourceFactories(ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());
	}
	
	public void registerPackages(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ExpressionsPackage.eINSTANCE.getNsURI(),
				ExpressionsPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
		
        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/5.0.0/UML", UMLPackage.eINSTANCE);		
		
		resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/1.0.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/2.0.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/2.1.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/2.2.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/2.3.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/3.0.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/3.1.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/4.0.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/5.0.0/UML", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/4.0.0/Types", TypesPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/5.0.0/Types", TypesPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(Ecore2XMLPackage.eNS_URI, Ecore2XMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.0", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.0.1", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.1", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.1.1", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.1.2", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.2", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.3", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.4", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/XMI/2.4.1", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.0", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.1", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.1.1", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.1.2", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.2", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.3", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.4", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.4.1", UMLPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put("http://schema.omg.org/spec/UML/2.5", UMLPackage.eINSTANCE);
	}
	
	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 */
	protected EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}
}
