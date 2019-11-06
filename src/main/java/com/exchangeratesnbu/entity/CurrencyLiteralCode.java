package com.exchangeratesnbu.entity;

public enum CurrencyLiteralCode {
    USD("Долар США", "US dollar"),
    EUR("Євро", "Euro"),
    PLN("Злотий", "Zloty"),
    GBP("Фунт стерлінгів", "Pound"),
    HUF("Форинт", "Forint"),
    CZK("Чеська крона", "Czech crown"),
    SEK("Шведська крона", "Swedish crown"),
    CHF("Швейцарський франк", "Swiss franc"),
    NOK("Норвезька крона", "Norwegian crown"),
    GEL("Ларi", "Lari"),
    CAD("Канадський долар", "Canadian dollar"),
    AUD("Австралійський долар", "Australian dollar"),
    AZN("Азербайджанський манат", "Azerbaijani manat"),
    DZD("Алжирський динар", "Algerian dinar"),
    BYN("Бiлоруський рубль", "Belarusian ruble"),
    THB("Бат", "Bat"),
    BGN("Болгарський лев", "Bulgarian lion"),
    AMD("Вiрменський драм", "Armenian dramas"),
    KRW("Вона", "Won"),
    GHS("Ганських седі", "Ghani sedi"),
    HKD("Гонконгівський долар", "Hong Kong dollar"),
    DKK("Данська крона", "Danish crown"),
    AED("Дирхам ОАЕ", "Dirham OAE"),
    VND("Донг", "Dong"),
    EGP("Єгипетський фунт", "Egyptian pound"),
    JPY("Єна", "Yen"),
    INR("Індійська рупія", "Indian rupee"),
    IQD("Іракський динар", "Iraqi dinar"),
    IRR("Іранський ріал", "Iranian rial"),
    HRK("Куна", "Kuhn"),
    LBP("Ліванський фунт", "Lebanese pound"),
    LYD("Лівійський динар", "Libyan dinar"),
    MYR("Малайзійський ринггіт", "Malaysian ringgit"),
    MAD("Марокканський дирхам", "Moroccan dirham"),
    MXN("Мексіканський песо", "Mexican peso"),
    MDL("Молдовський лей", "Moldovan leu"),
    ILS("Новий ізраїльський шекель", "New Israeli shekel"),
    TWD("Новий тайванський долар", "New Taiwan dollar"),
    NZD("Новозеландський долар", "New Zealand dollar"),
    PKR("Пакистанська рупія", "Pakistani rupee"),
    ZAR("Ренд", "Rand"),
    RUB("Російський рубль", "Russian ruble"),
    RON("Румунський лей", "Romanian lei"),
    IDR("Рупія", "Rupee"),
    SAR("Саудівський рiял", "Saudi ruled"),
    RSD("Сербський динар", "Serbian dinar"),
    SGD("Сінгапурський долар", "Singapore dollar"),
    KGS("Сом", "Som"),
    TJS("Сомонi", "Somoni"),
    BDT("Така", "Taka"),
    KZT("Теньге", "Tenegg"),
    TND("Туніський динар", "Tunisian dinar"),
    TRY("Турецька ліра", "Turkish lira"),
    TMT("Туркменський новий манат", "Turkmen new manat"),
    UZS("Узбецький сум", "Uzbek sums"),
    CNY("Юань Женьмiньбi", "Yuan zhenmini");

    CurrencyLiteralCode(String description, String descriptionEng) {
        this.description = description;
        this.descriptionEng = descriptionEng;
    }

    private String description;

    private String descriptionEng;

    public String getDescription() {
        return description;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }
}