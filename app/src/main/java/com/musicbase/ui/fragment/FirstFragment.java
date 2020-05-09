package com.musicbase.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.musicbase.R;
import com.musicbase.adapter.BtnListAdapter;
import com.musicbase.adapter.FirstAdapter;
import com.musicbase.entity.BtnListBean;
import com.musicbase.entity.FirstBean;
import com.musicbase.model.BannerImageLoader;
import com.musicbase.model.recyclerviewmodel.MultipleItem;
import com.musicbase.preferences.Preferences;
import com.musicbase.ui.activity.BrowerActivity;
import com.musicbase.ui.activity.CardActivity;
import com.musicbase.ui.activity.ContentListActivity;
import com.musicbase.ui.activity.DetailActivity;
import com.musicbase.ui.activity.SongScore;
import com.musicbase.ui.activity.WorkWebActivity;
import com.musicbase.ui.view.BtnListItemDecoration;
import com.musicbase.ui.view.SuperSwipeRefreshLayout;
import com.musicbase.util.ActivityUtils;
import com.musicbase.util.SPUtility;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.musicbase.preferences.Preferences.SUCCESS;
import static com.musicbase.preferences.Preferences.appInfo;


public class FirstFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SuperSwipeRefreshLayout refresh_view;

    @BindView(R.id.btn_scan)
    LinearLayout btnScan;
    @BindView(R.id.btn_search)
    LinearLayout btnSearch;
    @BindView(R.id.btn_record)
    LinearLayout btnRecord;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView2;
    private View mRootView;
    Unbinder unbinder2;
    private FirstAdapter adapter;

    private List<FirstBean.Data.ColumnList.CourseList> roundMapList;
    private List<FirstBean.Data.ColumnList> ordinaryList;
    private List<MultipleItem> dataList = new ArrayList<>();

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_first, container, false);
//        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        refresh_view.measure(0, 0);
        refresh_view.setRefreshing(true);
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
        unbinder2.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }


    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    private void initData() {
        // TODO Auto-generated method stub
        List<String> imagepaths = new ArrayList<>();
        for (FirstBean.Data.ColumnList.CourseList list : roundMapList) {
            imagepaths.add(list.getCourseImgPathHorizontal());
        }

        banner.setImages(imagepaths).setImageLoader(new BannerImageLoader(getActivity())).setDelayTime(2000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if (roundMapList.get(position).getClassification() == 1) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("courseId", roundMapList.get(position).getCourseId());
                            startActivity(intent);
                        } else if (roundMapList.get(position).getClassification() == 2) {
                            Intent intent = new Intent(getActivity(), BrowerActivity.class);
                            intent.putExtra("filePath", roundMapList.get(position).getCourseUrl());
                            intent.putExtra("name", roundMapList.get(position).getCourseName());
                            startActivity(intent);
                        }
                    }
                }).start();

        adapter.setNewData(dataList);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        // TODO Auto-generated method stub
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.list);
        refresh_view = (SuperSwipeRefreshLayout) mRootView.findViewById(R.id.swipe);

        refresh_view.setOnRefreshListener(this);
        refresh_view.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        adapter = new FirstAdapter(getActivity(), null);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_first, null);
        unbinder2 = ButterKnife.bind(this, header);
        setBtnList();
        adapter.addHeaderView(header);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataList.get(position).getItemType() == MultipleItem.HEAD) {
                    Intent intent = new Intent(getActivity(), ContentListActivity.class);
                    intent.putExtra("id", dataList.get(position).getCourseId());
                    intent.putExtra("content", dataList.get(position).getContent());
                    startActivity(intent);
                } else if (dataList.get(position).getItemType() == MultipleItem.CONTENT_1) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("courseId", dataList.get(position).getBean().getCourseId());
                    startActivity(intent);
                } else if (dataList.get(position).getItemType() == MultipleItem.CONTENT_3) {
//                    Intent intent = new Intent(getActivity(), ContentListActivity.class);
//                    intent.putExtra("id", dataList.get(position).getCourseId());
//                    intent.putExtra("content", dataList.get(position).getContent());
//                    startActivity(intent);
                    Intent intent = new Intent(getActivity(), BrowerActivity.class);
                    intent.putExtra("filePath", dataList.get(position).getBean().getCourseUrl() + "?userId=" + SPUtility.getUserId(getActivity()));
                    intent.putExtra("name", dataList.get(position).getBean().getCourseName());
                    startActivity(intent);
                }
            }
        });
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return dataList.get(position).getSpanSize();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * 中间按键列表
     */
    private int[] im = {R.mipmap.assistant_04, R.mipmap.assistant_06, R.mipmap.assistant_09, R.mipmap.assistant_03};
    //    private int[] im = {R.mipmap.assistant_01, R.mipmap.assistant_02, R.mipmap.assistant_03, R.mipmap.assistant_04, R.mipmap.assistant_05, R.mipmap.assistant_06, R.mipmap.assistant_07, R.mipmap.assistant_08};
    private String[] name = {"师资认证", "你唱我评", "学习卡", "画板"};
    //    private String[] name = {"能力评测", "模拟考试", "趣练", "师资认证", "直播", "你唱我评", "节拍器", "更多"};
    private List<BtnListBean> list = new ArrayList<>();

    private void setBtnList() {
        for (int i = 0; i < 4; i++) {
            BtnListBean bean = new BtnListBean();
            bean.setIm(im[i]);
            bean.setName(name[i]);
            list.add(bean);
            bean = null;
        }
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView2.addItemDecoration(new BtnListItemDecoration(getActivity()));//间距
        BtnListAdapter adapter = new BtnListAdapter(getActivity(), R.layout.item_btnlist, list);
        recyclerView2.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 1:
                        startActivity(new Intent(getActivity(), SongScore.class));
                        break;

                    case 3:
                        Intent intent3 = new Intent(getActivity(), WorkWebActivity.class);
                        intent3.putExtra("filePath", "file:///android_asset/draw/index.html");
                        intent3.putExtra("name", "画板");
                        intent3.putExtra("fileType", "html");
                        startActivity(intent3);
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), CardActivity.class));

                        break;

                    default:
                        ActivityUtils.showToast(getActivity(), "此功能暂未开放，敬请期待！");
                        break;
                }
            }
        });
    }


    private void requestData() {
        Logger.d("userid===" + SPUtility.getUserId(getActivity()));
        OkGo.<String>post(Preferences.HOME_INFO).tag(this)
                .params("userId", SPUtility.getUserId(getActivity()) + "")
                .params("AppInfo", appInfo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<FirstBean>() {
                        }.getType();
                        FirstBean bean = null;
                        try {

                            bean = gson.fromJson(response.body(), type);

                        } catch (JsonSyntaxException e) {

                            e.printStackTrace();

                        }
                        if (bean == null)
                            return;
                        if (bean.getCode().equalsIgnoreCase(SUCCESS)) {
                            ordinaryList = bean.getData().getColumnList();
                            roundMapList = bean.getData().getCourseRoundMapList();
                            transforToData();
                            initData();
                        } else {
                            ActivityUtils.showToast(getActivity(), "出现错误：" + bean.getCodeInfo());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (isAdded())
                            ActivityUtils.showToast(getActivity(), getString(R.string.load_fail) + ",请检查网络");
                    }

                    @Override
                    public void onFinish() {
                        if (refresh_view.isRefreshing())
                            refresh_view.setRefreshing(false);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<FirstBean>() {
                        }.getType();
                        FirstBean bean = gson.fromJson(response.body(), type);
                        if (bean != null) {
                            ordinaryList = bean.getData().getColumnList();
                            roundMapList = bean.getData().getCourseRoundMapList();
                            transforToData();
                            initData();
                        }
                    }
                });

//        RequestParams params = new RequestParams(Preferences.HOME_INFO);
//        params.addQueryStringParameter("userId", SPUtility.getUserId(getActivity()) + "");
//        Logger.d("userid===" + SPUtility.getUserId(getActivity()));
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//                Logger.d("HOME_INFO===" + s);
//                Gson gson = new Gson();
//                Type type = new TypeToken<FirstBean>() {
//                }.getType();
//                FirstBean bean = gson.fromJson(s, type);
//                if (bean != null) {
//                    ordinaryList = bean.getData().getColumnList();
//                    roundMapList = bean.getData().getCourseRoundMapList();
//                    transforToData();
//                    initData();
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//                if (isAdded())
//                    ActivityUtils.showToast(getActivity(), getString(R.string.load_fail) + ",请检查网络");
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//            }
//
//            @Override
//            public void onFinished() {
//                if (refresh_view.isRefreshing())
//                    refresh_view.setRefreshing(false);
//            }
//        });

    }

    int[] imIds = {R.mipmap.yueqi_01, R.mipmap.yueqi_02, R.mipmap.yueqi_03, R.mipmap.yueqi_04, R.mipmap.yueqi_04, R.mipmap.yueqi_06, R.mipmap.yueqi_05};

    //转换数据
    private void transforToData() {
        dataList.clear();
        for (int i = 0; i < ordinaryList.size(); i++) {
            FirstBean.Data.ColumnList data = ordinaryList.get(i);
            if (data.getSystemCodeName().equals("名家名师") && data.getCourseList().size() < 3)
                continue;
            else if (data.getCourseList().size() < 1)
                continue;
            dataList.add(new MultipleItem(MultipleItem.HEAD, MultipleItem.HEAD_SIZE, data.getSystemCodeId(), data.getSystemCodeName(), imIds[i]));
            for (int j = 0; j < data.getCourseList().size(); j++) {
                FirstBean.Data.ColumnList.CourseList course = data.getCourseList().get(j);
                if (course.getClassification() == 1) {
                    dataList.add(new MultipleItem(MultipleItem.CONTENT_1, MultipleItem.CONTENT_1_SIZE, course));
                } else if (course.getClassification() == 2) {
                    dataList.add(new MultipleItem(MultipleItem.CONTENT_3, MultipleItem.CONTENT_3_SIZE, course));
                } else if (course.getClassification() == 3) {
                    dataList.add(new MultipleItem(MultipleItem.CONTENT_1, MultipleItem.CONTENT_1_SIZE, course));
                }
            }
        }
    }


    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        refresh_view.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestData();
            }
        }, 0);

    }

    @OnClick({R.id.btn_scan, R.id.btn_search, R.id.btn_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                break;
            case R.id.btn_search:
                break;
            case R.id.btn_record:
                break;
        }
    }
}
