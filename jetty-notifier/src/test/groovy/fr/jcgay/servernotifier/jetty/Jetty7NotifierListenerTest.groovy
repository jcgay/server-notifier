package fr.jcgay.servernotifier.jetty
import fr.jcgay.notification.Notification
import fr.jcgay.notification.Notifier
import groovy.transform.CompileStatic
import org.eclipse.jetty.util.component.LifeCycle
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

import static org.mockito.Mockito.inOrder
import static org.mockito.Mockito.verify

@CompileStatic
class JettyNotifierListenerTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule()

    @InjectMocks
    JettyNotifierListener listener

    @Mock
    Notifier notifier

    @Mock
    LifeCycle lifeCycle

    @Test
    void 'should send notification when server has started'() {
        listener.lifeCycleStarted(lifeCycle)

        verify(notifier).send(Notification.builder('Jetty', 'Server has started !', Icons.jetty()).build())
    }

    @Test
    void 'should send notification and close notifier when server has stopped'() {
        listener.lifeCycleStopped(lifeCycle)

        def ordered = inOrder(notifier)
        ordered.verify(notifier).send(Notification.builder('Jetty', 'Server has stopped !', Icons.jetty()).build())
        ordered.verify(notifier).close()
    }
}