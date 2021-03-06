package project.jeonghoon.com.nooncoaching;

/**
 * Created by han on 2015-11-24.
 */
/**


public class foodDbJson extends AsyncTask<String,Void,Void> {


    private GpsInfo gps;
    double latitude;
    double longitude;
    int startItemNumber = 5;

    int[] randomArrayIndex = new int[5];


    @Override
    protected void onPostExecute(Void aVoid) {

        Random random = new Random();

        for( int i = 0; i < 5; i++ ){
            randomArrayIndex[i] = random.nextInt(15);

            for(int j=0;j<i;j++) {
                if(randomArrayIndex[i] == randomArrayIndex[j]) {
                    randomArrayIndex[i] = random.nextInt(15);
                    i = i-1;
                    break;
                }
            }
        }


        DBHandler dh = DBHandler.open(MainActivity.mContext);
        if (staticMerge.temp.equals("hot") || staticMerge.temp.equals("rain")){
            dh.selectfood();
        }else{
            dh.selectFood();
        }


        gps = new GpsInfo(MainActivity.mContext);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();


        try{

            int radius = 3000;
            int page = 1;
            if(!(staticMerge.foodAnni.get(0).equals("empty"))) {
                Searcher searcher1_1,searcher1_2,searcher1_3,searcher1_4,searcher1_5;
                switch (staticMerge.foodAnni.size()){
                    case 1:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY , new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);


                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        break;
                    case 2:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_2 = new Searcher();
                        searcher1_2.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(1), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY , new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(1)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(1, item);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(1, itemList.get(0));
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        break;

                    case 3:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY , new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_2 = new Searcher();
                        searcher1_2.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(1), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(1)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(1, item);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);
                                } else {
                                    MainActivity.ThemaItem.set(1, itemList.get(0));
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_3 = new Searcher();
                        searcher1_3.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(2), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(2)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";

                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(2, item);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(2, itemList.get(0));
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        break;

                    case 4:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY , new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_2 = new Searcher();
                        searcher1_2.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(1), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(1)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(1, item);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);
                                } else {
                                    MainActivity.ThemaItem.set(1, itemList.get(0));
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_3 = new Searcher();
                        searcher1_3.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(2), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(2)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";

                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(2, item);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(2, itemList.get(0));
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_4 = new Searcher();
                        searcher1_4.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(3), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(3)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";

                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(3, item);
                                    MainActivity.ThemaItem.set(4, item2);
                                } else {
                                    MainActivity.ThemaItem.set(3, itemList.get(0));
                                    MainActivity.ThemaItem.set(4, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        break;

                    default:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY , new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_2 = new Searcher();
                        searcher1_2.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(1), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(1)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(1, item);
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);
                                } else {
                                    MainActivity.ThemaItem.set(1, itemList.get(0));
                                    MainActivity.ThemaItem.set(2, item2);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_3 = new Searcher();
                        searcher1_3.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(2), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(2)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";

                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(2, item);
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                } else {
                                    MainActivity.ThemaItem.set(2, itemList.get(0));
                                    MainActivity.ThemaItem.set(3, item2);
                                    MainActivity.ThemaItem.set(4, item2);

                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_4 = new Searcher();
                        searcher1_4.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(3), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(3)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";

                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(3, item);
                                    MainActivity.ThemaItem.set(4, item2);
                                } else {
                                    MainActivity.ThemaItem.set(3, itemList.get(0));
                                    MainActivity.ThemaItem.set(4, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });

                        searcher1_5 = new Searcher();
                        searcher1_5.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(4), latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(4)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";

                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(4, item);
                                } else {
                                    MainActivity.ThemaItem.set(4, itemList.get(0));
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        break;

                }
            }else {
                Item item = new Item();
                item.title = "오늘에 해당하는 ";
                item.category = " ";
                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                item.address = "추천 주소가 없습니다.";
                item.phone = "기념일이 없습니다.";
                MainActivity.ThemaItem.set(0, item);
                MainActivity.ThemaItem.set(1, item);
                MainActivity.ThemaItem.set(2, item);
                MainActivity.ThemaItem.set(3, item);
                MainActivity.ThemaItem.set(4, item);

            }
            Log.i("aaaa", "aaaaaaaaaaaaaaaaa2" + staticMerge.finish_food[0]);



            if(staticMerge.recommendation_category[0].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(5, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.recommendation_category[0], latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if(itemList.size() == 0){

                        }else{

                            MainActivity.ThemaItem.set(startItemNumber, itemList.get(0));
                            startItemNumber++;

                            Log.i("aaaa", "aaaaaaaaaaaaaaaaa2  " + startItemNumber);
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }

            if(staticMerge.recommendation_category[1].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(6, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.recommendation_category[1], latitude, longitude, radius, page, MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if (itemList.size() == 0) {

                        } else {

                            MainActivity.ThemaItem.set(startItemNumber, itemList.get(0));
                            startItemNumber++;


                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }
            if(staticMerge.recommendation_category[2].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(7, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.recommendation_category[2], latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if(itemList.size() == 0){

                        }else{

                            MainActivity.ThemaItem.set(startItemNumber, itemList.get(0));
                            startItemNumber++;

                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }
            if(staticMerge.recommendation_category[3].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(8, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.recommendation_category[3], latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if(itemList.size() == 0){

                        }else{

                            MainActivity.ThemaItem.set(startItemNumber, itemList.get(0));
                            startItemNumber++;

                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }
            if(staticMerge.recommendation_category[4].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(9, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.recommendation_category[4], latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if(itemList.size() == 0){

                        }else{

                            MainActivity.ThemaItem.set(startItemNumber, itemList.get(0));
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }
            Log.i("aaaa", "aaaaaaaaaaaaaaaaa2" + startItemNumber);



            OldSearcher searcher3 = new OldSearcher();
            searcher3.searchCategory(MainActivity.mContext, "FD6", latitude, longitude, radius, page,2,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                @Override
                public void onSuccess(List<Item> itemList) {
                    Item item = new Item();
                    item.title = "가까운 거리에";
                    item.category = "음식점이 없습니다.";
                    item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                    item.address = "추천 주소가 없습니다.";
                    item.phone = "추천할만한";
                    if(itemList.size() == 0){
                        MainActivity.ThemaItem.set(10, item);
                        MainActivity.ThemaItem.set(11, item);
                        MainActivity.ThemaItem.set(12, item);
                        MainActivity.ThemaItem.set(13, item);
                        MainActivity.ThemaItem.set(14, item);
                    }else{
                        switch (itemList.size()){
                            case 1:
                                MainActivity.ThemaItem.set(10, itemList.get(0));
                                MainActivity.ThemaItem.set(11, item);
                                MainActivity.ThemaItem.set(12, item);
                                MainActivity.ThemaItem.set(13, item);
                                MainActivity.ThemaItem.set(14, item);
                                break;
                            case 2:
                                MainActivity.ThemaItem.set(10, itemList.get(0));
                                MainActivity.ThemaItem.set(11, itemList.get(1));
                                MainActivity.ThemaItem.set(12, item);
                                MainActivity.ThemaItem.set(13, item);
                                MainActivity.ThemaItem.set(14, item);
                                break;
                            case 3:
                                MainActivity.ThemaItem.set(10, itemList.get(0));
                                MainActivity.ThemaItem.set(11, itemList.get(1));
                                MainActivity.ThemaItem.set(12, itemList.get(2));
                                MainActivity.ThemaItem.set(13, item);
                                MainActivity.ThemaItem.set(14, item);
                                break;
                            case 4:
                                MainActivity.ThemaItem.set(10, itemList.get(0));
                                MainActivity.ThemaItem.set(11, itemList.get(1));
                                MainActivity.ThemaItem.set(12, itemList.get(2));
                                MainActivity.ThemaItem.set(13, itemList.get(3));
                                MainActivity.ThemaItem.set(14, item);
                            default:
                                MainActivity.ThemaItem.set(10, itemList.get(0));
                                MainActivity.ThemaItem.set(11, itemList.get(1));
                                MainActivity.ThemaItem.set(12, itemList.get(2));
                                MainActivity.ThemaItem.set(13, itemList.get(3));
                                MainActivity.ThemaItem.set(14, itemList.get(4));
                                break;
                        }
                    }
                }

                @Override
                public void onFail() {
                }
            });
            OldSearcher searcher4 = new OldSearcher();
            searcher4.searchCategory(MainActivity.mContext, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 4), MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                @Override
                public void onSuccess(List<Item> itemList) {
                    Item item = new Item();
                    item.title = "무작위로";
                    item.category = "음식점이 없습니다.";
                    item.address = "추천 주소가 없습니다.";
                    item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
                    item.phone = "추천할만한";
                    if (itemList.size() == 0) {
                        MainActivity.ThemaItem.set(15, item);
                        MainActivity.ThemaItem.set(16, item);
                        MainActivity.ThemaItem.set(17, item);
                        MainActivity.ThemaItem.set(18, item);
                        MainActivity.ThemaItem.set(19, item);

                        SaveData svData = new SaveData(MainActivity.mContext);
                        svData.save("SharedFood");
                        staticMerge.timer = true;
                    } else {

                        switch (itemList.size()) {
                            case 1:
                                MainActivity.ThemaItem.set(15, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(16, item);
                                MainActivity.ThemaItem.set(17, item);
                                MainActivity.ThemaItem.set(18, item);
                                MainActivity.ThemaItem.set(19, item);

                                break;
                            case 2:
                                MainActivity.ThemaItem.set(15, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(16, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(17, item);
                                MainActivity.ThemaItem.set(18, item);
                                MainActivity.ThemaItem.set(19, item);
                                break;
                            case 3:
                                MainActivity.ThemaItem.set(15, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(16, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(17, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(18, item);
                                MainActivity.ThemaItem.set(19, item);
                                break;
                            case 4:
                                MainActivity.ThemaItem.set(15, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(16, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(17, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(18, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(19, item);
                                break;
                            default:
                                MainActivity.ThemaItem.set(15, itemList.get(randomArrayIndex[0]));
                                MainActivity.ThemaItem.set(16, itemList.get(randomArrayIndex[1]));
                                MainActivity.ThemaItem.set(17, itemList.get(randomArrayIndex[2]));
                                MainActivity.ThemaItem.set(18, itemList.get(randomArrayIndex[3]));
                                MainActivity.ThemaItem.set(19, itemList.get(randomArrayIndex[4]));
                                break;
                        }
                        SaveData svData = new SaveData(MainActivity.mContext);
                        svData.save("SharedFood");

                        staticMerge.timer = true;


                    }

                }

                @Override
                public void onFail() {
                }
            });
        }catch (Exception e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("aaaa", exceptionAsStrting);
        }


        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... params) {
        ArrayList<String> foodDB = new ArrayList<String>();

        try {
            URL url = new URL("http://222.116.135.79:8080/Noon/createJson.jsp?" + params[0]);
            Log.i("param0", "" + params[0]);
            URLConnection conn = (HttpURLConnection) url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            http.setChunkedStreamingMode(0);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
            //InputStream in = http.getInputStream();

            StringBuffer sb = new StringBuffer();
            String myResult = "";
            try {
                int chr;
                while ((chr = in.read()) != -1) {
                    sb.append((char) chr);
                }
                myResult = sb.toString();
                Log.i("param0", myResult);
            } finally {
                in.close();
            }
            try {
                JSONObject root = new JSONObject(myResult);
                JSONArray jarr = root.getJSONArray("Food");
                String food_name = "";
                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject json = new JSONObject();
                    json = jarr.getJSONObject(i);
                    food_name = json.getString("food_name");
                    foodDB.add(food_name);
                    Log.i("param0", food_name);
                }

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("error", exceptionAsStrting);
            }

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("error", exceptionAsStrting);
        }

        // 오늘에 해당하는 기념일이 있는지 검색하려나 보다
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        int month,day;
        month = Integer.parseInt(CurMonthFormat.format(date));
        day = Integer.parseInt(CurDayFormat.format(date));
        DBHandler dh = DBHandler.open(MainActivity.mContext);
        Log.i("cccc",""+month+" "+day);
        ArrayList<Anni> Annis = dh.selectAnniWithWhere(month-1, day);
        String food_nameAnniv = "";

        // 받아온 기념일 객체가 없다면 이 루틴 영번째 파람은 날씨와 관련된 파람---음력과 양력에 관한 것
        // 첫번째 파람은 primary 키:양력 날씨 에 해당하는 음식이 있는지에 대한 검사
        // 두번째는 같은 방법으로 음력
        //0  String param = "tbName=noon_food&col=food_name&food_wea="+weather+"&food_time="+query;
        //1  String param2 = "tbName=anniv&col=food_name&SoL=S&date="+today_S;
        //2  String param3 =  "tbName=anniv&col=food_name&SoL=L&date="+today_L;
        if(Annis == null){
            try {
                URL url = new URL("http://222.116.135.79:8080/Noon/createJson.jsp?" + params[1]);
                Log.i("param1", "" + params[1]);
                URLConnection conn = (HttpURLConnection) url.openConnection();
                HttpURLConnection http = (HttpURLConnection) conn;

                http.setChunkedStreamingMode(0);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("POST");

                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
                //InputStream in = http.getInputStream();
                StringBuffer sb = new StringBuffer();
                String myResult = "";
                try {
                    int chr;
                    while ((chr = in.read()) != -1) {
                        sb.append((char) chr);
                    }
                    myResult = sb.toString();
                    Log.i("param1", myResult);
                } finally {
                    in.close();
                }
                try {
                    JSONObject root = new JSONObject(myResult);
                    JSONArray jarr = root.getJSONArray("Food");
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = new JSONObject();
                        json = jarr.getJSONObject(i);
                        food_nameAnniv = json.getString("food_name");
                        Log.i("param1", food_nameAnniv);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsStrting = sw.toString();
                    Log.e("error", exceptionAsStrting);
                }

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("error", exceptionAsStrting);
            }

            if(food_nameAnniv.equals("empty")){
                try {
                    URL url = new URL("http://222.116.135.79:8080/Noon/createJson.jsp?" + params[2]);
                    Log.i("param2", "" + params[2]);
                    URLConnection conn = (HttpURLConnection) url.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    http.setChunkedStreamingMode(0);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");

                    BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
                    //InputStream in = http.getInputStream();

                    StringBuffer sb = new StringBuffer();
                    String myResult = "";
                    try {
                        int chr;
                        while ((chr = in.read()) != -1) {
                            sb.append((char) chr);
                        }
                        myResult = sb.toString();
                        Log.i("param2", myResult);
                    } finally {
                        in.close();
                    }
                    try {
                        JSONObject root = new JSONObject(myResult);
                        JSONArray jarr = root.getJSONArray("Food");
                        food_nameAnniv = "";
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject json = new JSONObject();
                            json = jarr.getJSONObject(i);
                            food_nameAnniv = json.getString("food_name");
                            Log.i("param2", food_nameAnniv);
                        }

                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
                        String exceptionAsStrting = sw.toString();
                        Log.e("error", exceptionAsStrting);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsStrting = sw.toString();
                    Log.e("error", exceptionAsStrting);
                }
                if(food_nameAnniv.equals("empty")){

                    staticMerge.finish_food[3]="empty";
                    staticMerge.finish_food[4] ="empty";
                }else{
                    staticMerge.finish_food[3]=food_nameAnniv;
                }
            }else{
                staticMerge.finish_food[3]=food_nameAnniv;
            }
        }else{//여기가 끝 ㅋㅋㅋㅋ
            staticMerge.finish_food[3] = "empty";
            staticMerge.finish_food[4] = Annis.get(0).getCate();
        }
        staticMerge.foodAnni.clear();
        if(!staticMerge.finish_food[3].equals("empty")){
            staticMerge.foodAnni.add(staticMerge.finish_food[3]);
        }else if(!staticMerge.finish_food[4].equals("empty")){
            try {

                String query = URLEncoder.encode(staticMerge.finish_food[4], "utf-8");
                URL url2 = new URL("http://222.116.135.79:8080/Noon/createJson.jsp?" + "tbName=noon_food&col=food_name&food_wea="+staticMerge.temp+"&food_ann="+query);
                Log.i("param3","http://222.116.135.79:8080/Noon/createJson.jsp?" + "tbName=noon_food&col=food_name&food_wea="+staticMerge.temp+"&food_ann="+query);
                URLConnection conn2 = (HttpURLConnection) url2.openConnection();
                HttpURLConnection http2 = (HttpURLConnection) conn2;
                ArrayList<String> foodAnni = new ArrayList<>();
                http2.setChunkedStreamingMode(0);
                http2.setDoInput(true);
                http2.setDoOutput(true);
                http2.setRequestMethod("POST");
                BufferedReader in2 = new BufferedReader(new InputStreamReader(http2.getInputStream(), "UTF-8"));
                //InputStream in = http.getInputStream();
                foodAnni.clear();

                StringBuffer sb2 = new StringBuffer();
                String myResult = "";

                try {
                    int chr;
                    while ((chr = in2.read()) != -1) {
                        sb2.append((char) chr);
                    }
                    myResult = sb2.toString();
                    Log.i("param3", myResult);
                } finally {
                    in2.close();
                }
                try {
                    JSONObject root = new JSONObject(myResult);
                    JSONArray jarr = root.getJSONArray("Food");
                    String food_name = "";
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = new JSONObject();
                        json = jarr.getJSONObject(i);
                        food_name = json.getString("food_name");
                        foodAnni.add(food_name);
                        Log.i("param3", food_name);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsStrting = sw.toString();
                    Log.e("error", exceptionAsStrting);
                }
                staticMerge.foodAnni = foodAnni;
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("error", exceptionAsStrting);
            }
        }else{
            staticMerge.foodAnni.add("empty");
        }

        staticMerge.food = foodDB;
        Log.i("cccc",staticMerge.foodAnni.get(0));
        return null;
    }

}*/
