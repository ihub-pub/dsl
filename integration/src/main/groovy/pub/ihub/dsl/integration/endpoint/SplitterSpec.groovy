package pub.ihub.dsl.integration.endpoint


import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.dsl.SplitterEndpointSpec
import org.springframework.integration.splitter.AbstractMessageSplitter
import org.springframework.integration.splitter.DefaultMessageSplitter
import org.springframework.integration.splitter.ExpressionEvaluatingSplitter
import org.springframework.integration.splitter.MethodInvokingSplitter
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer
import java.util.function.Function



/**
 * 拆分器
 *
 * @param <P> the expected {@code payload} type.
 * @author liheng
 */
class SplitterSpec<P> extends AEndpointSpec<P, Function<P, ?>, SplitterEndpointSpec> {

    String builderMethodName = 'split'
    private AbstractMessageSplitter splitter

    SplitterSpec(Consumer<SplitterEndpointSpec<DefaultMessageSplitter>> endpointConfigurer = null) {
        this.endpointConfigurer = endpointConfigurer
    }

    SplitterSpec(AbstractMessageSplitter splitter,
                 Consumer<SplitterEndpointSpec<? extends AbstractMessageSplitter>> endpointConfigurer = null) {
        this.splitter = splitter
        this.endpointConfigurer = endpointConfigurer
    }

    SplitterSpec(String expression,
                 Consumer<SplitterEndpointSpec<ExpressionEvaluatingSplitter>> endpointConfigurer = null) {
        super(expression, endpointConfigurer)
    }

    SplitterSpec(String beanName, String methodName,
                 Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        super(beanName, methodName, endpointConfigurer)
    }

    SplitterSpec(Object service, String methodName = null,
                 Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        super(service, methodName, endpointConfigurer)
    }

    SplitterSpec(Class<P> payloadType, Function<P, ?> splitter,
                 Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        super(payloadType, splitter, endpointConfigurer)
    }

    SplitterSpec(Function<P, ?> splitter, Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer) {
        this(null, splitter, endpointConfigurer)
    }

    SplitterSpec(MessageProcessorSpec processorSpec,
                 Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        super(processorSpec, endpointConfigurer)
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        (super << builder) ?: splitter ? builder.split(splitter, endpointConfigurer) :
                endpointConfigurer ? builder.split(endpointConfigurer) : builder.split()
    }

}