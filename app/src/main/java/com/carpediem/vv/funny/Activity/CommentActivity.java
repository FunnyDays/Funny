package com.carpediem.vv.funny.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.FunnyGIF.Comment;
import com.carpediem.vv.funny.bean.FunnyGIF.FunnyGif;
import com.carpediem.vv.funny.bean.Userbean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/8/31.
 */
public class CommentActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Comment> arrayList=new ArrayList<>();
    private CommentAdapter commentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String stringExtraObjectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);
        initData();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        initSwipeRefreshLayout();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.comment_listview);
        initListView();
        TextView tvEmpty = (TextView) findViewById(R.id.tv_empty);
        listView.setEmptyView(tvEmpty);

        Button btCommit = (Button) findViewById(R.id.bt_commit);
        final EditText etComment = (EditText) findViewById(R.id.et_comment);
        assert btCommit != null;
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                String textComment = etComment.getText().toString().trim();
                if (textComment!="")
                comment.setContent(textComment);
                arrayList.add(comment);
                commentAdapter.notifyDataSetChanged();
                commitComment(textComment);
                etComment.setText("");
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etComment.getWindowToken(),0);
            }


        });
    }
    private void commitComment(String textComment) {
        final MyUser user = MyUser.getCurrentUser(MyUser.class);
        Log.i("bmob","评论发表成功"+user.getUsername());
        Log.i("bmob","评论发表成功"+user.getModel());
        if(user != null){
            // 允许用户使用应用
            FunnyGif funnyGif = new FunnyGif();
            funnyGif.setObjectId(stringExtraObjectId);
            final Comment comment = new Comment();
            comment.setContent(textComment);
            comment.setFunnyGif(funnyGif);

            comment.setUser(user);
            comment.save(new SaveListener<String>() {

                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        Log.i("bmob","评论发表成功"+ user.getObjectId());

                    }else{
                        Log.i("bmob","失败："+e.getMessage());
                    }
                }

            });
        }else{
            //注册界面
            //MyUser user = new MyUser();
        }

    }
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshData();

            }
        });
    }

    private void onRefreshData() {
        Log.e("bmob查询的数据","CommentAcitityinitData");
        BmobQuery<Comment> query = new BmobQuery<Comment>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        FunnyGif funnyGif = new FunnyGif();
        Intent intent = getIntent();
        stringExtraObjectId = intent.getStringExtra("ObjectId");
        Log.e("bmob查询的数据", stringExtraObjectId +"id");
        funnyGif.setObjectId(stringExtraObjectId);
        query.addWhereEqualTo("funnyGif",new BmobPointer(funnyGif));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user");
        query.order("createdAt");
        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> objects, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                arrayList.clear();
                for (Comment comment : objects) {
                    Log.e("bmob查询的数据",comment.getContent()+ "更多的护具查询：curPage");
                    Log.e("bmob查询的数据",comment.getUser().getUsername()+ "更多的护具查询：curPage");

                    arrayList.add(comment);
                    commentAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void initData() {
        Log.e("bmob查询的数据","CommentAcitityinitData");
        BmobQuery<Comment> query = new BmobQuery<Comment>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        FunnyGif funnyGif = new FunnyGif();
        Intent intent = getIntent();
        stringExtraObjectId = intent.getStringExtra("ObjectId");
        Log.e("bmob查询的数据", stringExtraObjectId +"id");
        funnyGif.setObjectId(stringExtraObjectId);
        query.addWhereEqualTo("funnyGif",new BmobPointer(funnyGif));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user");
        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> objects, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                for (Comment comment : objects) {

                    Log.e("bmob查询的数据",comment.getContent()+ "更多的护具查询：curPage");
                   Log.e("bmob查询的数据",comment.getUser().getUsername()+ "更多的护具查询：curPage");

                    arrayList.add(comment);
                    commentAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void initListView() {
        commentAdapter = new CommentAdapter();
        listView.setAdapter(commentAdapter);
    }
    class CommentAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(CommentActivity.this, R.layout.item_comment_list, null);
                viewHolder.text_name = (TextView) convertView.findViewById(R.id.id_name);
                viewHolder.text_comment = (TextView) convertView.findViewById(R.id.tv_comment);
                viewHolder.image_phone = (ImageView) convertView.findViewById(R.id.iv_photo);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
           if (arrayList!=null) {
                if(arrayList.get(position).getUser()!=null){
                    viewHolder.text_name.setText(arrayList.get(position).getUser().getModel());

                }else {
                    viewHolder.text_name.setText(android.os.Build.MODEL);
                }

               viewHolder.text_comment.setText(arrayList.get(position).getContent());


            }
            return convertView;
        }
    }
    public final class ViewHolder {
        public TextView text_name;
        public TextView text_comment;
        public ImageView image_phone;

    }
}
