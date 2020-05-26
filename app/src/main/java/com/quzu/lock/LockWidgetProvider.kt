package com.quzu.lock

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.RemoteViews
import com.ttlock.bl.sdk.api.TTLockClient
import com.ttlock.bl.sdk.callback.ControlLockCallback
import com.ttlock.bl.sdk.constant.ControlAction
import com.ttlock.bl.sdk.entity.LockError


class LockWidgetProvider : AppWidgetProvider(){
    var doorLockData1 = "tBenf/qnQSeg2P+JvhMSaoD0rinhnk4exNdanlr8OcvBnejLdHz1LmN9T1JEkhGIihB8aZWhrq0jCyM4cZQH3qhi/0xbn2sTvjvgQY5enzxeVH3BsiKMBftmTUfRpAMXIfiFRAJGxffrQBTV9L7JnKNkXwNl6Jo1JhmnqwkIidWFlcjQsUJB4X+KG2Zp98+uS/S/VMEVlzVO/mZuEGItDcORjai26dkSTw/oIVbttD6ctKIfqhRvoT62XvzxS3iYxTlySbJoOY8HMK0DWIK97X25UyRp4YU1kaHushAywafUQkRgX9aeoVPn39O5Z3jz8vPqPC9XlI0kY1Ey0XeeAPFQp0AZ+7I0HlTB0DOMObdoNKAtzO4e7ESJ3RgVqoRpXI4/iQyjnAfFpsit5pRBKyfQCFd5LgvFCGuNC9HJw7OztC2XdxvVa/QodgJaxxrYZ7fkGLD670A5M0ewzR0FqIheKBgzAXro5pdp+X1RYw9HY5DaT2AIwaMM0qVgKC92UlPtmBVU+AgV4AKTaqoM6LA3CStq48oxlEChJZx0BO8es/UDIz2yG603yjyHvZbLJPJeiJgUMNHZk8hL2aDn5U9H6w4PeOM3ZLzhM2hx39bscVttEbAsMDzQREjLJgkF2XRUVDxe"
    var doorLockData2 = "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"

    /**
     * 接收窗口小部件点击时发送的广播
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        var data = intent?.data
        var resID = -1
        if(data != null){
            resID = Integer.parseInt(data.getSchemeSpecificPart());
        }

        var views = RemoteViews(context?.packageName, R.layout.widget_lock)
        when(resID){
            R.id.big_btn -> { openLock(context, views, R.id.big_btn, "大门开锁", doorLockData1, "D9:74:54:54:3C:5E")}
            R.id.small_btn -> { openLock(context, views, R.id.small_btn, "小门开锁", doorLockData2, "F6:A0:D0:BD:D6:2A") }
        }

        ug(context, views)
        super.onReceive(context, intent)
    }

    private fun ug(context: Context?, btn: RemoteViews){
        var awm = AppWidgetManager.getInstance(context)
        var componentName = context?.let { ComponentName(it, LockWidgetProvider::class.java) }
        awm.updateAppWidget(componentName, btn)
    }

    fun openLock(context: Context?, btn: RemoteViews, resID: Int, text: String, lockData: String, lockMac: String) {
        if(!TTLockClient.getDefault().isBLEEnabled(context)){
            showToast(context,"把蓝牙打开！！！！！！")
            return
        }

        btn.setTextViewText(resID, "开锁中....")
        ug(context, btn)
        TTLockClient.getDefault().controlLock(ControlAction.UNLOCK, lockData, lockMac, object: ControlLockCallback {
            override fun onControlLockSuccess(lockAction: Int, battery: Int, uniqueId: Int) {
                btn.setTextViewText(resID, "开锁成功")
                ug(context, btn)
                Handler().postDelayed({
                    btn.setTextViewText(resID, text)
                    ug(context, btn)
                }, 2000)
            }

            override fun onFail(error: LockError) {
                btn.setTextViewText(resID, "开锁失败")
                ug(context, btn)
                Handler().postDelayed({
                    btn.setTextViewText(resID, text)
                    ug(context, btn)
                }, 2000)
            }
        })
    }

    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.e("LockWidgetProvider", "开始了更新");
        initWidget(context, appWidgetIds)
    }

    private fun initWidget(context: Context?, ids: IntArray?){
        var views = RemoteViews(context?.packageName, R.layout.widget_lock)
        initListener(context, views)
        update(context, ids, views) //通知系统更新widget---否则，对界面的修改及设置的监听事件等均无效
    }

    //设置控件的点击事件
    private fun initListener(context: Context?, views: RemoteViews?){
        views?.setOnClickPendingIntent(R.id.big_btn, getPendingIntent(context, R.id.big_btn))
        views?.setOnClickPendingIntent(R.id.small_btn, getPendingIntent(context, R.id.small_btn))
    }

    private fun getPendingIntent(context: Context?, resID: Int): PendingIntent{
        var intent = Intent()
        if (context != null) {
            intent.setClass(context, LockWidgetProvider::class.java)
        }
        intent.setData(Uri.parse("harvic:" + resID))
        var pendingIntent = PendingIntent.getBroadcast(context, 0, intent,0)
        return pendingIntent
    }

    private fun update(context: Context?, ids: IntArray?, views: RemoteViews?){
        var widgetMng = AppWidgetManager.getInstance(context)
        if(ids?.isNotEmpty()!!){
            widgetMng.updateAppWidget(ids, views)
        } else {
            widgetMng.updateAppWidget(context?.let { ComponentName(it, javaClass) }, views)
        }
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.i("LockWidgetProvider", "删除成功！")
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.i("LockWidgetProvider", "创建成功！")
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.i("LockWidgetProvider", "删除成功！")
    }

    /**
     * 当小部件大小改变时
     */
    override fun onAppWidgetOptionsChanged(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, newOptions: Bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    override fun onRestored(context: Context, oldWidgetIds: IntArray, newWidgetIds: IntArray) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }

    fun showToast(context: Context?, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}