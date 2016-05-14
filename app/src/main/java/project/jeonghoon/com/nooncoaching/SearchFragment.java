package project.jeonghoon.com.nooncoaching;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
// search Location problem,
public class SearchFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener {

    private static final String LOG_TAG = "SearchFragment";

    private MapView mMapView;
    private EditText mEditTextQuery;
    private Button mButtonSearch;
    private HashMap<Integer, Item> mTagItemMap = new HashMap<Integer, Item>();
    TextView nameView;
    TextView telView;
    TextView cateView;
    TextView addrView;
    ImageView cookImage;
    Button bSaveRestaurant;
    Item currentItem;
    double latitude;
    double longitude;
    int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
    int page = 1;
    String apikey = MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY;
    String defaultImageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
    private GpsInfo gps;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        Log.i(LOG_TAG, "onCreateView had loaded. Now, MapView APIs could be called safely");
        mMapView = (MapView)rootView.findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setMapViewEventListener(this);
        mMapView.setPOIItemEventListener(this);
        mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        nameView = (TextView)rootView.findViewById(R.id.nameView);
        telView = (TextView)rootView.findViewById(R.id.telView);
        cateView = (TextView)rootView.findViewById(R.id.cateView);
        addrView = (TextView)rootView.findViewById(R.id.addrView);
        cookImage = (ImageView)rootView.findViewById(R.id.cookImage);
        bSaveRestaurant = (Button)rootView.findViewById(R.id.bSaveRestaurant);

        mEditTextQuery = (EditText) rootView.findViewById(R.id.editTextQuery); // 검색창

        mButtonSearch = (Button) rootView.findViewById(R.id.buttonSearch); // 검색버튼

        Log.d(LOG_TAG, "검색 프래그먼트 진입");
        //gps 사용
        gps = new GpsInfo(getActivity());


        latitude = gps.getLatitude();
        longitude = gps.getLongitude();


        //검색 버튼 리스너
        mButtonSearch.setOnClickListener(new OnClickListener() { // 검색버튼 클릭 이벤트 리스너
            @Override
            public void onClick(View v) {
                String query = mEditTextQuery.getText().toString();
                if (query == null || query.length() == 0) {
                    showToast("검색어를 입력하세요.");
                    return;
                }
                hideSoftKeyboard(); // 키보드 숨김

                showToast("" + latitude);

                OldSearcher searcher = new OldSearcher(); // net.daum.android.map.openapi.search.Searcher
                searcher.searchKeyword(getActivity().getApplicationContext(), query, latitude, longitude, radius, page, 2, apikey, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        mMapView.removeAllPOIItems(); // 기존 검색 결과 삭제
                        showResult(itemList); // 검색 결과 보여줌
                }

                    @Override
                    public void onFail() {
                        showToast("API_KEY의 제한 트래픽이 초과되었습니다.");
                    }
                });
                showToast("위도 = " + latitude + "\n경도 = " +longitude);
            }
        });

        //저장 버튼 리스너
        bSaveRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (currentItem == null) {
                    showToast("아이템을 클릭해주세요");
                }else {
                    DBHandler dbHandler = DBHandler.open(getActivity(), currentItem);
                    StringBuilder sb = new StringBuilder();
                    sb.append("title=").append(currentItem.title).append("\n");
                    sb.append("imageUrl=").append(currentItem.imageUrl).append("\n");
                    sb.append("address=").append(currentItem.address).append("\n");
                    sb.append("newAddress=").append(currentItem.newAddress).append("\n");
                    sb.append("zipcode=").append(currentItem.zipcode).append("\n");
                    sb.append("phone=").append(currentItem.phone).append("\n");
                    sb.append("category=").append(currentItem.category).append("\n");
                    sb.append("longitude=").append(currentItem.longitude).append("\n");
                    sb.append("latitude=").append(currentItem.latitude).append("\n");
                    sb.append("distance=").append(currentItem.distance).append("\n");
                    sb.append("direction=").append(currentItem.direction).append("\n");
                    dbHandler.click_time();
                    dbHandler.insertFavorItem();
                    dbHandler.close();
                    Toast.makeText(getActivity(), sb.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {

        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getActivity().getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            if (poiItem == null) return null;
            Item item = mTagItemMap.get(poiItem.getTag());
            if (item == null) return null;
            ImageView imageViewBadge = (ImageView) mCalloutBalloon.findViewById(R.id.badge);
            TextView textViewTitle = (TextView) mCalloutBalloon.findViewById(R.id.title);
            textViewTitle.setText(item.title);
            TextView textViewDesc = (TextView) mCalloutBalloon.findViewById(R.id.desc);
            textViewDesc.setText(item.address);
            imageViewBadge.setImageDrawable(createDrawableFromUrl(item.imageUrl));
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }

    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditTextQuery.getWindowToken(), 0);
    }

    public void onMapViewInitialized(MapView mapView) {
        Log.i(LOG_TAG, "MapView had loaded. Now, MapView APIs could be called safely");
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);

        mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude, longitude), 2, true);

        //initial query
        Searcher searcher = new Searcher();
        String query = mEditTextQuery.getText().toString();

        searcher.searchKeyword(getActivity().getApplicationContext(), query, latitude, longitude, radius, page, apikey, new OnFinishSearchListener() {
            @Override
            public void onSuccess(final List<Item> itemList) {
                showResult(itemList);

            }

            @Override
            public void onFail() {
                showToast("검색된 아이템이 없습니다.");
            }
        });

    }

    private void showToast(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showResult(List<Item> itemList) {
        MapPointBounds mapPointBounds = new MapPointBounds();

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            //url 이미지가 없으면 그냥 default 이미지 넣어줌
            if(item.getImageUrl().equals("")){
                item.setImageUrl(defaultImageUrl);
            }

            //첫번째 아이템이 셀렉 되기 때문에
            if ( i == 0 ){
                currentItem = item;
                setDetailView();
            }

            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setItemName(item.title);
            poiItem.setTag(i);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude);
            poiItem.setMapPoint(mapPoint);
            mapPointBounds.add(mapPoint);
            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomImageResourceId(R.drawable.map_pin_blue);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomSelectedImageResourceId(R.drawable.map_pin_red);
            poiItem.setCustomImageAutoscale(false);
            poiItem.setCustomImageAnchor(0.5f, 1.0f);

            mMapView.addPOIItem(poiItem);
            mTagItemMap.put(poiItem.getTag(), item);
        }

        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        MapPOIItem[] poiItems = mMapView.getPOIItems();
        if (poiItems.length > 0) {
            mMapView.selectPOIItem(poiItems[0], false);
        }
    }

    private Drawable createDrawableFromUrl(String url) {
        try {
            InputStream is = (InputStream) this.fetch(url);
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object fetch(String address) throws MalformedURLException,IOException {
        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    @Deprecated
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        currentItem = mTagItemMap.get(mapPOIItem.getTag());
        setDetailView();
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) {
    }
    public void setDetailView(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameView.setText(currentItem.title);
                cateView.setText(currentItem.category);
                addrView.setText(currentItem.address);
                telView.setText(currentItem.phone);

                new DownloadImageTask(cookImage).execute(currentItem.getImageUrl());

            }
        });

    }


}