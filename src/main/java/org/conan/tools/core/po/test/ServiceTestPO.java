/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conan.tools.core.po.test;

import java.util.List;
import java.util.Map;

import org.conan.tools.core.build.VelocityFactory;
import org.conan.tools.core.build.tree.ClazzTree;
import org.conan.tools.core.po.file.ClazzPO;
import org.conan.tools.core.xmlloader.ModelType;
import org.conan.tools.core.xmlloader.ModuleType;
import org.conan.tools.util.io.WriteFile;

/**
 * 
 * @author conan
 */
public class ServiceTestPO extends ClazzPO {

    public ServiceTestPO(String root, String basePackage, List<ModuleType> modules) {
        super(root, basePackage, modules);
    }

    public ServiceTestPO() {
    }

    @Override
    public void create() {
        for (ModuleType module : getModuleTypes()) {
            for (ModelType model : module.getModel()) {
                write(module, model);
            }
        }
    }

    void write(ModuleType module, ModelType model) {
        ClazzTree clazz = new ClazzTree(this.root, this.basePackage, module.getName(), model.getName());

        Map<String, Object> map = this.getVMMap();
        map.put("model", clazz.getModel());
        map.put("model_1", clazz.getModelLowercase());
        map.put("import_model", clazz.getModelPackageClazz());
        map.put("import_dao", clazz.getDAOPackageClazz());
        map.put("import_service", clazz.getServicePackageClazz());
        map.put("service_impl_package", clazz.getServiceImplPackage());

        VelocityFactory vf = new VelocityFactory(VelocityFactory.SERIVCE_TEST_VM, map);
        new WriteFile(clazz.getServiceImplTestFile(), vf.getWriter());
    }

}
