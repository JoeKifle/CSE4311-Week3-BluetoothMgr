package com.example.john.bluetoothmgrapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declare bluetooth adapter
    BluetoothAdapter bluetoothAdapter;

    private static final int REQUEST_BLUETOOTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if the device supports bluetooth

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null){
            // device doesn't support bluetooth
            Toast.makeText(this, "Device doesn't support Bluetooth",Toast.LENGTH_SHORT).show();
        }else{

            // check if bluetooth is enabled

            if(!bluetoothAdapter.isEnabled()){
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth,REQUEST_BLUETOOTH);
            }


        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if(requestCode == REQUEST_BLUETOOTH){

            if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                enableDiscoverability();
            }else{

                Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();

            }
        }

    }


    private final BroadcastReceiver deviceFoundReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                Toast.makeText(MainActivity.this, deviceName.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void enableDiscoverability(){


          // Making device discoverable  60 seconds

        Intent descoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        descoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,60);
        startActivity(descoverableIntent);


    }

}
