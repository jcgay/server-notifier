package fr.jcgay.servernotifier.tomcat
import fr.jcgay.notification.Notification
import fr.jcgay.notification.Notifier
import groovy.transform.CompileStatic
import org.apache.catalina.Lifecycle
import org.apache.catalina.LifecycleEvent
import org.apache.catalina.LifecycleState
import org.apache.catalina.core.StandardContext
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

import static org.apache.catalina.Lifecycle.*
import static org.apache.catalina.LifecycleState.*
import static org.mockito.Mockito.*

@CompileStatic
class Tomcat7NotifierListenerTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule()

    @Mock
    private Notifier notifier

    @Mock
    private StandardContext context

    @InjectMocks
    private TomcatNotifierListener listener

    @Test
    void 'should send notification when application has started'() {
        listener.lifecycleEvent(event('success', AFTER_START_EVENT, STARTED))

        verify(notifier).send(Notification.builder('Tomcat', 'success has started !', Icons.success()).build())
    }

    @Test
    void 'should send notification and close notifier when application has stopped'() {
        listener.lifecycleEvent(event('stop', AFTER_STOP_EVENT, STOPPED))

        def ordered = inOrder(notifier)
        ordered.verify(notifier).send(Notification.builder('Tomcat', 'stop has stopped !', Icons.tomcat()).build())
        ordered.verify(notifier).close()
    }

    @Test
    void 'should send notification when application startup has failed'() {
        listener.lifecycleEvent(event('failure', BEFORE_STOP_EVENT, FAILED))

        verify(notifier).send(Notification.builder('Tomcat', 'failure has failed to start !', Icons.failure()).build())
        verify(notifier, never()).send(Notification.builder('Tomcat', 'failure has started !', Icons.success()).build())
    }

    @Test
    void 'should close notifier when application has stopped'() {
        listener.lifecycleEvent(event('stop', AFTER_STOP_EVENT, FAILED))

        verify(notifier).close()
    }

    @Test
    void 'should send notification when server has started'() {
        listener.lifecycleEvent(server(AFTER_START_EVENT, STARTED))

        verify(notifier).send(Notification.builder('Tomcat', 'Tomcat has started !', Icons.success()).build())
    }

    private LifecycleEvent event(String name, String phase, LifecycleState state) {
        when(context.getName()).thenReturn(name)
        when(context.getState()).thenReturn(state)
        new LifecycleEvent(context, phase, null)
    }

    private LifecycleEvent server(String phase, LifecycleState state) {
        def server = mock(Lifecycle)
        when(server.getState()).thenReturn(state)
        new LifecycleEvent(server, phase, null)
    }
}