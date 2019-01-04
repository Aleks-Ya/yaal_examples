package hello.container;

public class Visit {
    private String dateTime;
    private Integer siteName;
    private Integer posaContinent;
    private Integer userLocationCountry;
    private Integer userLocationRegion;
    private Integer userLocationCity;
    private Double origDestinationDistance;
    private Integer userId;
    private Boolean isMobile;
    private Boolean isPackage;
    private Integer channel;
    private String srchCi;
    private String srchCo;
    private Integer srchAdultsCnt;
    private Integer srchChildrenCnt;
    private Integer srchRmCnt;
    private Integer srchDestinationId;
    private Integer srchDestinationTypeId;
    private Boolean isBooking;
    private Long cnt;
    private Integer hotelContinent;
    private Integer hotelCountry;
    private Integer hotelMarket;
    private Integer hotelCluster;

    public Visit(String dateTime, Integer siteName, Integer posaContinent, Integer userLocationCountry, Integer userLocationRegion, Integer userLocationCity, Double origDestinationDistance, Integer userId, Boolean isMobile, Boolean isPackage, Integer channel, String srchCi, String srchCo, Integer srchAdultsCnt, Integer srchChildrenCnt, Integer srchRmCnt, Integer srchDestinationId, Integer srchDestinationTypeId, Boolean isBooking, Long cnt, Integer hotelContinent, Integer hotelCountry, Integer hotelMarket, Integer hotelCluster) {
        this.dateTime = dateTime;
        this.siteName = siteName;
        this.posaContinent = posaContinent;
        this.userLocationCountry = userLocationCountry;
        this.userLocationRegion = userLocationRegion;
        this.userLocationCity = userLocationCity;
        this.origDestinationDistance = origDestinationDistance;
        this.userId = userId;
        this.isMobile = isMobile;
        this.isPackage = isPackage;
        this.channel = channel;
        this.srchCi = srchCi;
        this.srchCo = srchCo;
        this.srchAdultsCnt = srchAdultsCnt;
        this.srchChildrenCnt = srchChildrenCnt;
        this.srchRmCnt = srchRmCnt;
        this.srchDestinationId = srchDestinationId;
        this.srchDestinationTypeId = srchDestinationTypeId;
        this.isBooking = isBooking;
        this.cnt = cnt;
        this.hotelContinent = hotelContinent;
        this.hotelCountry = hotelCountry;
        this.hotelMarket = hotelMarket;
        this.hotelCluster = hotelCluster;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Integer getSiteName() {
        return siteName;
    }

    public Integer getPosaContinent() {
        return posaContinent;
    }

    public Integer getUserLocationCountry() {
        return userLocationCountry;
    }

    public Integer getUserLocationRegion() {
        return userLocationRegion;
    }

    public Integer getUserLocationCity() {
        return userLocationCity;
    }

    public Double getOrigDestinationDistance() {
        return origDestinationDistance;
    }

    public Integer getUserId() {
        return userId;
    }

    public Boolean isMobile() {
        return isMobile;
    }

    public Boolean getIsPackage() {
        return isPackage;
    }

    public Integer getChannel() {
        return channel;
    }

    public String getSrchCi() {
        return srchCi;
    }

    public String getSrchCo() {
        return srchCo;
    }

    public Integer getSrchAdultsCnt() {
        return srchAdultsCnt;
    }

    public Integer getSrchChildrenCnt() {
        return srchChildrenCnt;
    }

    public Integer getSrchRmCnt() {
        return srchRmCnt;
    }

    public Integer getSrchDestinationId() {
        return srchDestinationId;
    }

    public Integer getSrchDestinationTypeId() {
        return srchDestinationTypeId;
    }

    public Boolean isBooking() {
        return isBooking;
    }

    public Long getCnt() {
        return cnt;
    }

    public Integer getHotelContinent() {
        return hotelContinent;
    }

    public Integer getHotelCountry() {
        return hotelCountry;
    }

    public Integer getHotelMarket() {
        return hotelMarket;
    }

    public Integer getHotelCluster() {
        return hotelCluster;
    }


    @Override
    public String toString() {
        return "Visit{" +
                "dateTime='" + dateTime + '\'' +
                ", siteName=" + siteName +
                ", posaContinent=" + posaContinent +
                ", userLocationCountry=" + userLocationCountry +
                ", userLocationRegion=" + userLocationRegion +
                ", userLocationCity=" + userLocationCity +
                ", origDestinationDistance=" + origDestinationDistance +
                ", userId=" + userId +
                ", isMobile=" + isMobile +
                ", isPackage=" + isPackage +
                ", channel=" + channel +
                ", srchCi='" + srchCi + '\'' +
                ", srchCo='" + srchCo + '\'' +
                ", srchAdultsCnt=" + srchAdultsCnt +
                ", srchChildrenCnt=" + srchChildrenCnt +
                ", srchRmCnt=" + srchRmCnt +
                ", srchDestinationId=" + srchDestinationId +
                ", srchDestinationTypeId=" + srchDestinationTypeId +
                ", isBooking=" + isBooking +
                ", cnt=" + cnt +
                ", hotelContinent=" + hotelContinent +
                ", hotelCountry=" + hotelCountry +
                ", hotelMarket=" + hotelMarket +
                ", hotelCluster=" + hotelCluster +
                '}';
    }
}
