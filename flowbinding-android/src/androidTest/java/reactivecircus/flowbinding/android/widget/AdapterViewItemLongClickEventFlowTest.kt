package reactivecircus.flowbinding.android.widget

import android.widget.ListView
import androidx.test.filters.LargeTest
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBe
import org.junit.Test
import reactivecircus.flowbinding.android.fixtures.widget.ListFragment
import reactivecircus.flowbinding.android.test.R
import reactivecircus.flowbinding.testing.FlowRecorder
import reactivecircus.flowbinding.testing.launchTest
import reactivecircus.flowbinding.testing.longClickAdapterViewItemAt
import reactivecircus.flowbinding.testing.recordWith

@LargeTest
class AdapterViewItemLongClickEventFlowTest {

    @Test
    fun adapterViewItemLongClickEvents() {
        launchTest<ListFragment> {
            val recorder = FlowRecorder<AdapterViewItemLongClickEvent>(testScope)
            val listView = getViewById<ListView>(R.id.listView)

            listView.itemLongClickEvents().recordWith(recorder)

            recorder.assertNoMoreValues()

            longClickAdapterViewItemAt(R.id.listView, 2)
            val event = recorder.takeValue()
            event.view shouldEqual listView
            event.longClickedView shouldNotBe null
            event.position shouldEqual 2
            event.id shouldEqual 2

            recorder.assertNoMoreValues()

            cancelTestScope()

            longClickAdapterViewItemAt(R.id.listView, 1)
            recorder.assertNoMoreValues()
        }
    }

    @Test
    fun adapterViewItemLongClickEvents_notHandled() {
        launchTest<ListFragment> {
            val recorder = FlowRecorder<AdapterViewItemLongClickEvent>(testScope)
            val listView = getViewById<ListView>(R.id.listView)

            listView.itemLongClickEvents { false }.recordWith(recorder)

            recorder.assertNoMoreValues()

            longClickAdapterViewItemAt(R.id.listView, 2)
            recorder.assertNoMoreValues()

            cancelTestScope()

            longClickAdapterViewItemAt(R.id.listView, 1)
            recorder.assertNoMoreValues()
        }
    }
}
