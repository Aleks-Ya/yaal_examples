package xmlparser

import groovy.xml.XmlNodePrinter
import groovy.xml.XmlParser
import org.junit.jupiter.api.Test

import java.nio.file.Files

/**
 * Дополнение конфига плагина Кристалл-Сервис.
 * Комментарии из xml-файла не сохраняются!
 */
class CrystalPluginConfig {
    def xmlPrinter = new XmlNodePrinter()

    @Test
    void parse() {
        def file = Files.createTempFile('CrystalPluginConfig', '.tmp').toFile()
        file.deleteOnExit()

        def configFile = new File(CrystalPluginConfig.class.getResource('goods-weight-config.xml').file.replace('%5e', '^').replace('%5E', '^'))
        file.write(configFile.text)
        def key = 'minWeight'
        def value = '10'
        changePluginConfigProperty(configFile.absolutePath, key, value)
        println file.text
    }

    /**
     * В конфигурационном файле плагина заменяет/добавляет элемент <property> с заданными атрибутами key и value.
     */
    private void changePluginConfigProperty(String configFile, String key, String value) {
        def weightConfig = new File(configFile)
        if (!weightConfig.exists()) {
            throw new RuntimeException("Not exists: ${weightConfig.absolutePath}")
        }
        def moduleConfig = new XmlParser().parse(weightConfig)
        xmlPrinter.print(moduleConfig)
        def properties = moduleConfig.property.findAll { key == it.@key }
        if (properties.size() > 1) {
            throw new RuntimeException("Duplicate property with key '${key}' in ${weightConfig.absolutePath}")
        }
        if (properties.empty) {
            moduleConfig.appendNode('property', [key: 'minWeight', value: '10'])
        } else {
            properties[0].@value = value
        }
        def writer = new PrintWriter(weightConfig)
        new XmlNodePrinter(writer).print(moduleConfig)
        writer.close()
        xmlPrinter.print(moduleConfig)
    }
}