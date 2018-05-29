package com.sacombank.consumers.views.qr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.sacombank.consumers.R;
import com.sacombank.consumers.utils.Utils;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.home.HomeFragment;

import java.util.List;

/**
 * Created by TranVietThuc on 15/08/2017.
 */

public class PaymentQRScannerFragment extends BaseFragment {
    private static final String TAG = PaymentQRScannerFragment.class.getSimpleName();
    private DecoratedBarcodeView scanner;
    private static final int REQUEST_CAMERA = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissionSystem();
    }

    private void checkPermissionSystem() {
        checkPermissionCamera();
    }

    private void checkPermissionCamera() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "CAMERA permissions has NOT been granted. Requesting permissions.");
            requestCameraPermissions();
        }
    }

    private void requestCameraPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Camera permission has now been granted.");
            } else {
                Log.i(TAG, "Camera permission was NOT granted.");
                requestCameraPermissions();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paymentscanner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        scanner.resume();
        updateCommonUI(PaymentQRScannerFragment.class.getSimpleName(), "Thanh toán QR");
    }

    @Override
    public void onPause() {
        super.onPause();

        scanner.pause();
    }

    private void addControllers(View view, Bundle savedInstanceState) {
        scanner = (DecoratedBarcodeView) view.findViewById(R.id.zxing_barcode_scanner);
        scanner.decodeContinuous(callback);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                scanner.pause();
                processPushFragment(PaymentByQRCodeFragment.newInstance(result.getText()), getBaseListener());
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    private void processPushFragment(BaseFragment fragment, BaseFragment.BaseListener baseListener) {
        if (fragment == null) return;
        fragment.setBaseListener(baseListener);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.acitivity_in_from_right_to_left, R.anim.activity_out_from_left);
        fragmentTransaction.replace(R.id.main_content, fragment);
        if (!(fragment instanceof HomeFragment))
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
