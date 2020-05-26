package com.quzu.lock

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import com.ttlock.bl.sdk.api.TTLockClient
import com.ttlock.bl.sdk.callback.ControlLockCallback
import com.ttlock.bl.sdk.constant.ControlAction
import com.ttlock.bl.sdk.entity.LockError
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var doorLockData1 = "tBenf/qnQSeg2P+JvhMSaoD0rinhnk4exNdanlr8OcvBnejLdHz1LmN9T1JEkhGIihB8aZWhrq0jCyM4cZQH3qhi/0xbn2sTvjvgQY5enzxeVH3BsiKMBftmTUfRpAMXIfiFRAJGxffrQBTV9L7JnKNkXwNl6Jo1JhmnqwkIidWFlcjQsUJB4X+KG2Zp98+uS/S/VMEVlzVO/mZuEGItDcORjai26dkSTw/oIVbttD6ctKIfqhRvoT62XvzxS3iYxTlySbJoOY8HMK0DWIK97X25UyRp4YU1kaHushAywafUQkRgX9aeoVPn39O5Z3jz8vPqPC9XlI0kY1Ey0XeeAPFQp0AZ+7I0HlTB0DOMObdoNKAtzO4e7ESJ3RgVqoRpXI4/iQyjnAfFpsit5pRBKyfQCFd5LgvFCGuNC9HJw7OztC2XdxvVa/QodgJaxxrYZ7fkGLD670A5M0ewzR0FqIheKBgzAXro5pdp+X1RYw9HY5DaT2AIwaMM0qVgKC92UlPtmBVU+AgV4AKTaqoM6LA3CStq48oxlEChJZx0BO8es/UDIz2yG603yjyHvZbLJPJeiJgUMNHZk8hL2aDn5U9H6w4PeOM3ZLzhM2hx39bscVttEbAsMDzQREjLJgkF2XRUVDxe"
    var doorLockData2 = "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"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TTLockClient.getDefault().prepareBTService(this)

        getBluePermission()

        big_tv.setOnClickListener(){
            openLock(big_tv, "大门开锁", doorLockData1, "D9:74:54:54:3C:5E")
        }

        small_tv.setOnClickListener(){
            openLock(small_tv, "小门开锁", doorLockData2, "F6:A0:D0:BD:D6:2A")
        }
    }

    // 得到蓝牙权限
    private fun getBluePermission() {
        AndPermission.with(this)
            .runtime()
            .permission(PERMISSIONS_OF_LOCATION)
            .onGranted { }
            .onDenied { permissions -> showToast("要想开锁就要打开蓝牙权限和打开蓝牙") }
            .start()
    }

    fun openLock(btn: Button, text: String, lockData: String, lockMac: String) {
        if(!TTLockClient.getDefault().isBLEEnabled(this)){
            showToast("把蓝牙打开！！！！！！")
            return
        }

        btn.text = "开锁中.."
        TTLockClient.getDefault().controlLock(ControlAction.UNLOCK, lockData, lockMac, object: ControlLockCallback {
            override fun onControlLockSuccess(lockAction: Int, battery: Int, uniqueId: Int) {
                btn.text = "开锁成功"
                Handler().postDelayed({ btn.text = text}, 2000)
            }

            override fun onFail(error: LockError) {
                btn.text = "开锁失败"
                Handler().postDelayed({ btn.text = text}, 2000)
            }
        })
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    val PERMISSIONS_OF_LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onDestroy() {
        super.onDestroy()
        TTLockClient.getDefault().stopScanLock()
        TTLockClient.getDefault().stopBTService()
    }
}
