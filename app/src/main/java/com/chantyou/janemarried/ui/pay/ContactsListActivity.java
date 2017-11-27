package com.chantyou.janemarried.ui.pay;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.chantyou.janemarried.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/5.
 */
public class ContactsListActivity extends ListActivity implements View.OnClickListener {
    private final int UPDATE_LIST=1;
    ArrayList<String> contactsList; //得到的所有联系人
    ArrayList<String> getcontactsList; //选择得到联系人
    private Button okbtn;
    private Button title_back;
    private ProgressDialog proDialog;

    Thread getcontacts;
    Handler updateListHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case UPDATE_LIST:
                    if (proDialog != null) {
                        proDialog.dismiss();
                    }
                    updateList();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactsList=new ArrayList<String>();
        getcontactsList=new ArrayList<String>();

        final ListView listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        okbtn=(Button)findViewById(R.id.contacts_done_button);
        okbtn.setOnClickListener(this);
        title_back=(Button)findViewById(R.id.title_back);
        title_back.setOnClickListener(this);

        getcontacts=new Thread(new GetContacts());
        getcontacts.start();
        proDialog = ProgressDialog.show(this, "正在加载","加载中", true, true);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }


    void updateList(){
        if(contactsList!=null)
//            setListAdapter(new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_multiple_choice, contactsList));
        /*
        这里如果用上面的原生的item布局的话，文字颜色没法改变，在华为G9上显示的文字颜色是白色，根本看不清
        在红米2上显示的文字颜色是偏黑色，所以为了统一起来，就自己定义一个布局，设置文字颜色为黑色
         */

            setListAdapter(new ArrayAdapter<String>(this,
                    R.layout.item_contacts, contactsList));

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        if(((CheckedTextView)v).isChecked()){

            CharSequence num=((CheckedTextView)v).getText();
            getcontactsList.add("，"+num.toString());
        }
        if(!((CheckedTextView)v).isChecked()){
            CharSequence num=((CheckedTextView)v).getText();
            if((num.toString()).indexOf("[")>0){
                String phoneNum=num.toString().substring(0, (num.toString()).indexOf("\n"));
                getcontactsList.remove(phoneNum);
                Log.d("remove_num", ""+phoneNum);
            }else{
                getcontactsList.remove(num.toString());
                Log.d("remove_num", ""+num.toString());
            }
        }
        super.onListItemClick(l, v, position, id);
    }

    class GetContacts implements Runnable{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Uri uri = ContactsContract.Contacts.CONTENT_URI;
            String[] projection = new String[] {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_ID
            };
            String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'";
            String[] selectionArgs = null;
            String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
            Cursor cursor=managedQuery(uri, projection, selection, selectionArgs, sortOrder);
            Cursor phonecur = null;

            while (cursor.moveToNext()){

                // 取得联系人名字
                int nameFieldColumnIndex = cursor.getColumnIndex(android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME);
                String name = cursor.getString(nameFieldColumnIndex);
                // 取得联系人ID
                String contactId = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
                phonecur = managedQuery(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "  + contactId, null, null);
                // 取得电话号码(可能存在多个号码)
                while (phonecur.moveToNext()){
                    String strPhoneNumber = phonecur.getString(phonecur.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if(strPhoneNumber.length()>4)
//                        contactsList.add("电话："+strPhoneNumber+"\n"+name);
                        contactsList.add(" "+name+" ");
                    //contactsList.add(strPhoneNumber+"\n"+name+"");
                }
            }
            if(phonecur!=null)
                phonecur.close();
            cursor.close();

            Message msg1=new Message();
            msg1.what=UPDATE_LIST;
            updateListHandler.sendMessage(msg1);
        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        contactsList.clear();
        getcontactsList.clear();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.contacts_done_button:
                Intent i = new Intent();
//                Toast.makeText(this, " getcontactsList:::"+getcontactsList, Toast.LENGTH_SHORT).show();
                if(getcontactsList!=null&&getcontactsList.size()>0){
                    Bundle b = new Bundle();
                    b.putStringArrayList("GET_CONTACT", getcontactsList);
                    i.putExtras(b);
                }
                setResult(RESULT_OK, i);
                this.finish();
                break;
            case R.id.title_back:
                this.finish();
                break;
            default:
                break;
        }
    }

}
