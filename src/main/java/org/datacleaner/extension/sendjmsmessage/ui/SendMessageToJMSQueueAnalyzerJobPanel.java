package org.datacleaner.extension.sendjmsmessage.ui;

import java.util.HashMap;
import java.util.Map;

import org.datacleaner.api.InputColumn;
import org.datacleaner.descriptors.ConfiguredPropertyDescriptor;
import org.datacleaner.job.builder.AnalyzerComponentBuilder;
import org.datacleaner.job.builder.ComponentBuilder;
import org.datacleaner.panels.AnalyzerComponentBuilderPanel;
import org.datacleaner.widgets.properties.MultipleMappedStringsPropertyWidget;
import org.datacleaner.widgets.properties.PropertyWidget;
import org.datacleaner.widgets.properties.PropertyWidgetFactory;

import com.hi.datahub.dc.commons.PasswordPropertyWidget;

/**
 * Job panel class for Analyzer.
 *
 */
public class SendMessageToJMSQueueAnalyzerJobPanel extends AnalyzerComponentBuilderPanel {

    private static final long serialVersionUID = 1L;

    private final MultipleMappedStringsPropertyWidget _mappedWidget;
    private final ConfiguredPropertyDescriptor _inputColumnsProperty;
    private final ConfiguredPropertyDescriptor _mappedStringsProperty;

    private Map<ConfiguredPropertyDescriptor, PropertyWidget<?>> overriddenWidgets = new HashMap<ConfiguredPropertyDescriptor, PropertyWidget<?>>();

    /**
     * Constructor.
     * 
     * @param analyzerJobBuilder
     * @param propertyWidgetFactory
     */
    public SendMessageToJMSQueueAnalyzerJobPanel(AnalyzerComponentBuilder<?> analyzerJobBuilder,
            PropertyWidgetFactory propertyWidgetFactory) {
        super(analyzerJobBuilder, propertyWidgetFactory);

        _inputColumnsProperty = analyzerJobBuilder.getDescriptor().getConfiguredProperty("Values");
        _mappedStringsProperty = analyzerJobBuilder.getDescriptor().getConfiguredProperty("Fields");

        ConfiguredPropertyDescriptor passwordProperty = analyzerJobBuilder.getDescriptor().getConfiguredProperty("JMS queue password");
        PasswordPropertyWidget passwordPropertyWidget = new PasswordPropertyWidget(passwordProperty, analyzerJobBuilder);
        overriddenWidgets.put(passwordProperty, passwordPropertyWidget);

        _mappedWidget = new MultipleMappedStringsPropertyWidget(analyzerJobBuilder, _inputColumnsProperty,
                _mappedStringsProperty) {
            @Override
            protected String getDefaultMappedString(InputColumn<?> inputColumn) {
                return inputColumn.getName();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PropertyWidget<?> createPropertyWidget(ComponentBuilder componentBuilder,
            ConfiguredPropertyDescriptor propertyDescriptor) {
        if (overriddenWidgets.containsKey(propertyDescriptor)) {
            return overriddenWidgets.get(propertyDescriptor);
        } else if (propertyDescriptor == _inputColumnsProperty) {
            return _mappedWidget;
        } else if (propertyDescriptor == _mappedStringsProperty) {
            return _mappedWidget.getMappedStringsPropertyWidget();
        }
        return super.createPropertyWidget(componentBuilder, propertyDescriptor);
    }
}
