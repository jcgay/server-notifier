package fr.jcgay.servernotifier
import fr.jcgay.notification.Notification
import fr.jcgay.notification.Notifier
import groovy.transform.CompileStatic
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
    private Tomcat7NotifierListener listener

    @Test
    void 'should send notification when server has started'() {
        listener.lifecycleEvent(event('success', AFTER_START_EVENT, STARTED))

        verify(notifier).send(Notification.builder('Tomcat', 'Application [success] has started !', Icons.success()).build())
    }

    @Test
    void 'should send notification and close notifier when server has stopped'() {
        listener.lifecycleEvent(event('stop', AFTER_STOP_EVENT, STOPPED))

        def ordered = inOrder(notifier)
        ordered.verify(notifier).send(Notification.builder('Tomcat', 'Application [stop] has stopped !', Icons.tomcat()).build())
        ordered.verify(notifier).close()
    }

    @Test
    void 'should send notification when server startup has failed'() {
        listener.lifecycleEvent(event('failure', BEFORE_STOP_EVENT, FAILED))

        verify(notifier).send(Notification.builder('Tomcat', 'Application [failure] has failed to start !', Icons.failure()).build())
        verify(notifier, never()).send(Notification.builder('Tomcat', 'Application [failure] has started !', Icons.success()).build())
    }

    @Test
    void 'should close notifier when server has stopped'() {
        listener.lifecycleEvent(event('stop', AFTER_STOP_EVENT, FAILED))

        verify(notifier).close()
    }

    private LifecycleEvent event(String name, String phase, LifecycleState state) {
        when(context.getName()).thenReturn(name)
        when(context.getState()).thenReturn(state)
        new LifecycleEvent(context, phase, null)
    }
}