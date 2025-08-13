package com.optivem.atdd.acceptancetests.shared.channels.parametrized;

import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.*;

public class ChannelParameterizedExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod().isPresent() &&
               context.getTestMethod().get().isAnnotationPresent(ChannelParameterizedTest.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method testMethod = context.getTestMethod().get();
        ChannelParameterizedTest annotation = testMethod.getAnnotation(ChannelParameterizedTest.class);
        ChannelType[] channels = annotation.value();

        // Find the method source
        MethodSource methodSource = testMethod.getAnnotation(MethodSource.class);
        List<Object[]> parameters = new ArrayList<>();
        if (methodSource != null) {
            String source = methodSource.value().length > 0 ? methodSource.value()[0] : testMethod.getName();
            try {
                Method paramMethod = testMethod.getDeclaringClass().getDeclaredMethod(source);
                paramMethod.setAccessible(true);
                Stream<?> stream = (Stream<?>) paramMethod.invoke(null);
                stream.forEach(arg -> {
                    if (arg instanceof org.junit.jupiter.params.provider.Arguments) {
                        parameters.add(((org.junit.jupiter.params.provider.Arguments) arg).get());
                    } else if (arg instanceof Object[]) {
                        parameters.add((Object[]) arg);
                    } else {
                        parameters.add(new Object[]{arg});
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            parameters.add(new Object[0]);
        }

        return Arrays.stream(channels)
            .flatMap(channel -> parameters.stream()
                .map(params -> invocationContext(channel, params)));
    }

    private TestTemplateInvocationContext invocationContext(ChannelType channel, Object[] params) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "Channel: " + channel + ", Params: " + Arrays.toString(params);
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(
                    new ParameterResolver() {
                        @Override
                        public boolean supportsParameter(ParameterContext pc, ExtensionContext ec) {
                            int index = pc.getIndex();
                            if (pc.getParameter().getType() == ChannelType.class && index == 0) return true;
                            return index > 0 && index <= params.length &&
                                   pc.getParameter().getType() == String.class;
                        }

                        @Override
                        public Object resolveParameter(ParameterContext pc, ExtensionContext ec) {
                            int index = pc.getIndex();
                            if (pc.getParameter().getType() == ChannelType.class && index == 0) return channel;
                            return params[index - 1];
                        }
                    }
                );
            }
        };
    }
}