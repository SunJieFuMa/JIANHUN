/*
* Copyright (C) 2015 Andy Ke <dictfb@gmail.com>
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.administrator.litepal;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.litepal.litepal.Person;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//记住在布局文件中声明的onclick方法必须是public的，否则会报错
        super.onCreate(savedInstanceState);
        /*
        现在只要你对数据库有任何的操作，表就会被自动创建出来。
        LitePal提供了一个便捷的方法来获取到SQLiteDatabase的实例，如下所示：
        调用一下下面这行代码，表就应该已经创建成功了。
        或者不用调用，只要对数据库有任何的操作，表就会被自动创建出来。
         */
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        //        SQLiteDatabase db = Connector.getDatabase();
    }

    public void search(View view) {
        /*
        where()方法接收任意个字符串参数，其中第一个参数用于进行条件约束，
        从第二个参数开始，都是用于替换第一个参数中的占位符的。那这个where()方法就对应了一条SQL语句中的where部分。
         */
        List<Person> persons = DataSupport.where("id>?", "0").find(Person.class);
//        List<Person> persons2 = DataSupport.findAll(Person.class);//这样也行
        tv.setText(persons.toString());
    }

    public void update(View view) {
        ContentValues values = new ContentValues();
        values.put("name", "二狗2");
        //这段代码将id为1的person的name进行修改，注意update方法是DataSupport中的一个静态方法。
        //        DataSupport.update(Person.class, values, 1);//这个方法只能根据id来进行修改
        DataSupport.updateAll(Person.class, values, "name=?", "二狗");


        //        ContentValues values2 = new ContentValues();
        //        values.put("phoneNumber", "11111");
        //        DataSupport.updateAll(Phone.class, values2, "id=?", "1");
    }

    public void delete(View view) {
        DataSupport.deleteAll(Person.class, "name=?", "二狗");
//        int deleteCount = DataSupport.delete(Person.class, 1);//这个方法只能把根据id
//        System.out.println(deleteCount);
        /*
        删除id为1的person， 这里要多说几句了， 不就删除id是1的person么？
        肯定就是1条啊， 获取count是多余的吧？
        嘿嘿， 再次证明一下litepal的强大吧， 删除一条数据，
        litepal会把与该数据关联的其他表中的数据一并删除了，
        比如现在删除了id为1的Person， 那Phone表中属于Person的数据将全部被删除！！
        毕竟那些成为垃圾数据了。
         */
        //        DataSupport.deleteAll(Person.class, "id>?", "1");
        //        DataSupport.deleteAll(Person.class);//在不指定约束条件的情况下，deleteAll()方法就会删除表中所有的数据了。
        //        search();
    }

    public void add(View view) {
        //如果之前创建过这条数据，删除之后又创建了一遍，那么这条数据的id就会增加1
        Person p = new Person();//set方法就相当于向数据库中添加数据了
        p.setName("二狗");//因为继承了DataSupport的类就是创建了一张表，表的名字就是类名，所以这样就是往数据表中添加了数据了
        p.setGender("男");
        p.setPhoneNumber("123456");
        p.setHeight(100);
        p.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
    }
}
