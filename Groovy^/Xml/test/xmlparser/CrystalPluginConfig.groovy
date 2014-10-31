package xmlparser

import org.junit.Test

import java.nio.file.Files

/**
 * Дополнение конфига плагина Кристалл-Сервис.
 * Комментарии из xml-файла не сохраняются!
 */
class CrystalPluginConfig {
    def xmlPrinter = new XmlNodePrinter()

    @Test
    void parse() {
        File file  = Files.createTempFile('CrystalPluginConfig', '.tmp').toFile()
        file.deleteOnExit()

        File configFile = new File(CrystalPluginConfig.class.getResource('goods-weight-config.xml').file.replace('%5e', '^'))
        file.write(configFile.text)
        String key = 'minWeight'
        String value = 10
        changePluginConfigProperty(configFile.absolutePath, key, value)
        println file.text
    }

    /**
     * В конфигурационном файле плагина заменяет/добавляет элемент <property> с заданными атрибутами key и value.
     */
    private void changePluginConfigProperty(String configFile, String key, String value) {
        File weightConfig = new File(configFile)
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
        PrintWriter writer = new PrintWriter(weightConfig)
        new XmlNodePrinter(writer).print(moduleConfig)
        writer.close()
        xmlPrinter.print(moduleConfig)
    }
}