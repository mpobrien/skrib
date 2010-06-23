from com.google.inject import *
from skrib.models import *
from skrib.models.dao import *
from skrib.ctrlrs import HomeController
from skrib.modules import *
from com.mob.web import AppConfigModule
from java.io import File
modules = [AppConfigModule(File("conf/settings.properties"), [HomeController.getPackage()]),
           MongoModule(),
            MainServletModule() ] 
injector = Guice.createInjector(modules)
