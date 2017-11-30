package child.ryl.child.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import child.ryl.child.R;

/**
 * 不具备参考价值
 */
public class ContentFragment extends Fragment implements View.OnClickListener {
    private Button fragment_content_add;
    private Button fragment_content_delete;
    private Button fragment_content_update;
    private Button fragment_content_find;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fragment_content_add = (Button) view.findViewById(R.id.fragment_content_add);
        fragment_content_delete = (Button) view.findViewById(R.id.fragment_content_delete);
        fragment_content_update = (Button) view.findViewById(R.id.fragment_content_update);
        fragment_content_find = (Button) view.findViewById(R.id.fragment_content_find);
        fragment_content_add.setOnClickListener(this);
        fragment_content_delete.setOnClickListener(this);
        fragment_content_update.setOnClickListener(this);
        fragment_content_find.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_content_add:
                add();
                break;
            case R.id.fragment_content_delete:
                delete();
                break;
            case R.id.fragment_content_update:
                update();
                break;
            case R.id.fragment_content_find:
                queryPermission();
                break;
        }
    }

    private void add() {
    }

    /**
     * section 查询条件
     */
    private void queryPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            query();
        }
    }

    private void query() {
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.e("phone_tel", name + "\n" + phone);
                }
            }
        } catch (Exception e) {
            e.toString();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    private void update() {
    }

    private void delete() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    query();
                } else {
                    queryPermission();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
