package com.bcareapp.bookstore.controller;

import java.util.ArrayList;

public class DashboardResponse {
    public int appId;
    public ArrayList<BusinessList> businessList;
    public ArrayList<UserSelectableList> userSelectableList;
    public ArrayList<AdvertisementUrlList> advertisementUrlList;
    public ArrayList<BannerList> bannerList;
    public ArrayList<LabelList> labelList;
    public ArrayList<ShortcutList> shortcutList;
    public ArrayList<NearbyList> nearbyList;
    public ArrayList<AilmentList> ailmentList;
    public ArrayList<PopularVideoDoctorService> popularVideoDoctorServices;
    public String nearbyHotelsLabel;
    public ArrayList<BlogsList> blogsList;
    public ArrayList<NearbyHospitalsList> nearbyHospitalsList;
    public ArrayList<NearbyDiagnosticsList> nearbyDiagnosticsList;
    public ArrayList<NearbyWellnessList> nearbyWellnessList;
    public ArrayList<HealthtipsList> healthtipsList;
    public ArrayList<MostBookedDiagnosticList> mostBookedDiagnosticList;
    public class Address{
        public int city;
        public int state;
        public int area;
        public String cityName;
        public String stateName;
        public String areaName;
        public String pinCode;
        public LocationCoordinates locationCoordinates;
        public int addressId;
        public String address1;
        public String address2;
        public Location location;
        public int addressType;
    }

    public class AdvertisementUrlList{
        public int id;
        public String imageURL;
        public String header;
        public String message;
        public String deepLink;
        public boolean businessItem;
        public int pocType;
        public Location location;
        public String pinCode;
        public int priority;
    }

    public class AilmentList{
        public int id;
        public String imageURL;
        public String header;
        public String deepLink;
        public String name;
        public String imageUrl;
    }

    public class BannerList{
        public int id;
        public String imageURL;
        public String header;
        public String message;
        public String deepLink;
        public boolean businessItem;
    }

    public class BlogsList{
        public int parentProfileId;
        public Object comment;
        public int postedDate;
        public int healthTipId;
        public String name;
        public String description;
        public Object category;
        public Object subCategory;
        public Object tag1;
        public Object tag2;
        public Object tag3;
        public Object tag4;
        public Object tag5;
        public int tag1Priority;
        public int tag2Priority;
        public int tag3Priority;
        public int tag4Priority;
        public int tag5Priority;
        public Object healthRisks;
        public Object appIdList;
        public Object deepLink;
        public String thumbnailUrl;
        public int totalLikes;
        public int totalDislikes;
        public ArrayList<ContentUrl> contentUrls;
        public Object remedies;
        public int likeStatus;
    }

    public class BusinessList{
        public int id;
        public boolean checked;
        public String imageURL;
        public String header;
        public String message;
        public String deepLink;
        public boolean businessItem;
    }

    public class ContentUrl{
        public int type;
        public String contentUrl;
        public String contentName;
    }

    public class HealthtipsList{
        public int parentProfileId;
        public Object comment;
        public int postedDate;
        public int healthTipId;
        public String name;
        public String description;
        public ArrayList<String> category;
        public String subCategory;
        public String tag1;
        public String tag2;
        public String tag3;
        public String tag4;
        public String tag5;
        public int tag1Priority;
        public int tag2Priority;
        public int tag3Priority;
        public int tag4Priority;
        public int tag5Priority;
        public Object healthRisks;
        public ArrayList<Integer> appIdList;
        public Object deepLink;
        public Object thumbnailUrl;
        public int totalLikes;
        public int totalDislikes;
        public ArrayList<ContentUrl> contentUrls;
        public String remedies;
        public int likeStatus;
    }

    public class HomeOrderPriceDetails{
        public double grossPrice;
        public double discountPrice;
        public Object taxes;
        public double netPrice;
        public int type;
        public Object dayBasedPricing;
        public Object specialPriceList;
    }

    public class LabelList{
        public int id;
        public boolean checked;
        public String imageURL;
        public String header;
        public String message;
        public String deepLink;
        public boolean businessItem;
    }

    public class Location{
        public String type;
        public ArrayList<Double> coordinates;
    }

    public class LocationCoordinates{
        public double lon;
        public double lat;
    }

    public static class MostBookedDiagnosticList{
        public long grossPrice;
        public double discountPrice;
        public long netPrice;
        public int quantity;
        public double originalAmount;
        public double otherDiscountAmount;
        public double taxationAmount;
        public double finalAmount;
        public double discountType;
        public int serviceId;
        public String serviceName;
        public ArrayList<SubServiceList> subServiceList;
        public int parentServiceId;
        public String parentServiceName;
        public int categoryId;
        public String categoryName;
        public String imageUrl;
        public String precaution;
        public HomeOrderPriceDetails homeOrderPriceDetails;
        public WalkinOrderPriceDetails walkinOrderPriceDetails;
        public Object vacutainerList;
        public ArrayList<Note> note;
        public ArrayList<Object> displayDescriptionList;
        public String description;
        public int departmentId1;
        public int departmentId4;
        public String departmentName1;
        public String departmentName4;
        public String displayName;
        public String tags;
        public int doctorId;
        public int pocId;
        public int homeCollections;
        public int scheduleId;
        public int cityId;
        public int expiryDate;
        public int departmentId2;
        public String departmentName2;
        public int appId;
        public String couponCode;

        public MostBookedDiagnosticList(String serviceName,long grossPrice, long netPrice,long discountPrice  ) {
            this.grossPrice = grossPrice;
            this.netPrice = netPrice;
            this.serviceName = serviceName;
            this.discountPrice=discountPrice;
        }
    }

    public class NearbyDiagnosticsList{
        public int pocId;
        public String pocName;
        public Address address;
        public String locality;
        public String areaName;
        public String email;
        public int brandId;
        public int pdfHeaderType;
        public boolean payOnDeliveryAvailable;
        public boolean pharmacyHomeDeliveryAvailable;
        public boolean diagnosticSampleCollectionAvailable;
        public boolean productWalkinAvailable;
        public boolean productHomeDeliveryAvailable;
        public boolean localDiagnosticPartner;
        public boolean localPharmacyPartner;
        public int pocType;
        public double distance;
        public ArrayList<String> pocImageUrls;
        public String discountText;
        public ArrayList<String> contactList;
        public String pocLogo;
    }

    public class NearbyHospitalsList{
        public int pocId;
        public String pocName;
        public Address address;
        public String locality;
        public String areaName;
        public String email;
        public int brandId;
        public int pdfHeaderType;
        public boolean payOnDeliveryAvailable;
        public boolean pharmacyHomeDeliveryAvailable;
        public boolean diagnosticSampleCollectionAvailable;
        public boolean productWalkinAvailable;
        public boolean productHomeDeliveryAvailable;
        public boolean localDiagnosticPartner;
        public boolean localPharmacyPartner;
        public int pocType;
        public double distance;
        public ArrayList<String> contactList;
        public String pocLogo;
        public String discountText;
    }

    public class NearbyList{
        public int id;
        public int categoryId;
        public String categoryName;
        public String imageURL;
        public String header;
        public String message;
        public String deepLink;
        public boolean defaultItem;
    }

    public class NearbyWellnessList{
        public int pocId;
        public String pocName;
        public Address address;
        public String locality;
        public String areaName;
        public String email;
        public ArrayList<String> contactList;
        public String pocLogo;
        public int brandId;
        public int pdfHeaderType;
        public boolean payOnDeliveryAvailable;
        public boolean pharmacyHomeDeliveryAvailable;
        public boolean diagnosticSampleCollectionAvailable;
        public boolean productWalkinAvailable;
        public boolean productHomeDeliveryAvailable;
        public boolean localDiagnosticPartner;
        public boolean localPharmacyPartner;
        public int pocType;
        public double distance;
    }

    public class Note{
        public int symptomId;
        public String title;
    }

    public class PopularVideoDoctorService{
        public Object icon;
        public int categoryId;
        public Object categoryName;
        public int serviceId;
        public String serviceName;
        public Object displayName;
        public ArrayList<AilmentList> ailmentList;
        public ArrayList<Object> sub_categories;
        public int cityId;
        public int brandId;
        public boolean participatePhysically;
        public boolean participateVideoLater;
        public boolean disableConsumerBooking;
        public int id;
        public Object name;
        public Object summary;
        public int priority;
        public String imageUrl;
        public Object slugName;
    }




    public class ShortcutList{
        public int id;
        public boolean checked;
        public String header;
        public String deepLink;
        public boolean businessItem;
    }

    public class SubServiceList{
        public double grossPrice;
        public double discountPrice;
        public double netPrice;
        public int quantity;
        public double originalAmount;
        public double otherDiscountAmount;
        public double taxationAmount;
        public double finalAmount;
        public double discountType;
        public int serviceId;
        public String serviceName;
        public Object vacutainerList;
    }

    public static class UserSelectableList{
        public int id;
        public int categoryId;
        public String categoryName;
        public boolean checked;
        public String imageURL;
        public String header;
        public String message;
        public String deepLink;
        public boolean defaultItem;
        public boolean nonLoginItem;
        public UserSelectableList(String header){
            this.header= header;
        }
    }

    public class WalkinOrderPriceDetails{
        public double grossPrice;
        public double discountPrice;
        public Object taxes;
        public double netPrice;
        public int type;
        public Object dayBasedPricing;
        public Object specialPriceList;
    }
}
