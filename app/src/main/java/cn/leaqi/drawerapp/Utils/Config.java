package cn.leaqi.drawerapp.Utils;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Config {

    // 用户数据
    public static SparseArray<UserBean> UserList = new SparseArray<UserBean>(){{
        put(1, new UserBean(1, "微笑，向前行", "https://p.ssl.qhimg.com/t01f5b748f60e62c1b4.jpg" ,"https://p3.ssl.qhimgs1.com/dr/578_864_/t01e752a6a89da01e13.jpg" ,"湖北武汉大学" ,18 , 1,"要开学了，又有新生要来军训了，该准备西瓜了…"));
        put(2, new UserBean(2, "奔雷拳", "https://p.ssl.qhimg.com/t01c1c5570e3dbb0425.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_384_/t01be93565b295f6f2a.jpg" ,"湖北武汉" ,20 , 1,"眼睛别乱看，看我就行，这么帅的大帅哥站在你们面前都不知道看，一帮子傻孩子！"));
        put(3, new UserBean(3, "努力奔向目标", "https://p.ssl.qhimg.com/t01447ac7de787f8fba.jpg" ,"https://p3.ssl.qhimgs1.com/dr/578_384_/t0118d9c58168abac5d.jpg" ,"内蒙呼和浩特" , 2,30 ,"小心点啊同学们，我这个人轻易不出手，出手必伤人，伤人必见血，见血必死人！"));
        put(4, new UserBean(4, "未来丶为我们而来", "https://p.ssl.qhimg.com/t01f3e5713b8d1df758.jpg" ,"https://p1.ssl.qhimgs1.com/dr/578_384_/t014581243af77e697b.jpg" ,"山西太原" , 2,36 ,"求晴天，求高温，求四十度求，暴晒求，没风，我们苦点没关系；一定要让高一的学弟学妹们，有个良好的军训环境。"));
        put(5, new UserBean(5, "剑舞天涯", "https://p.ssl.qhimg.com/t010a3c59594ee46c1c.jpg" ,"https://p1.ssl.qhimgs1.com/dr/578_432_/t014353b3af6d1a9f87.jpg" ,"山西朔州" ,41 , 2,"站军姿时，某教官看到其他方阵有人晕倒，于是转过身对他方阵的人说：我们这儿的人不许倒下啊！倒下了我就当没看到，踩着他过去……众人无语。"));
        put(6, new UserBean(6, "少女总统", "https://p.ssl.qhimg.com/t01c99f6e988f439ff2.jpg" ,"https://p5.ssl.qhimgs1.com/dr/578_670_/t01b223399f42c6b305.jpg" ,"广东深圳" ,22 , 2,"又有一大批“包青天”准备进入中学的大门。"));
        put(7, new UserBean(7, "遇见就不再错过", "https://p.ssl.qhimg.com/t01b078a864fbdd6676.jpg" ,"https://p2.ssl.qhimgs1.com/dr/578_384_/t01115afe13160b8d28.jpg" ,"广东广州" ,23 , 2,"You have no idea how I’m feeling so shut up."));
        put(8, new UserBean(8, "女人你给力吗", "https://p.ssl.qhimg.com/t015198455bf5f8b610.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_432_/t011e61455d215da766.jpg" ,"福建厦门" ,35 , 2,"你以为放手可以成全我的幸福，可你不知道我最大的幸福就是和你手牵手。"));
        put(9, new UserBean(9, "一生都得酷", "https://p.ssl.qhimg.com/t01227f53f9ed7daea7.jpg" ,"https://p1.ssl.qhimgs1.com/dr/578_386_/t015189a3a4f35210fb.jpg" ,"福建福州" ,32 , 2,"青春如同奔流的江河，一点一滴的流淌着。"));
        put(10, new UserBean(10, "我绝版了i", "https://p.ssl.qhimg.com/t01b9f72e02098beb90.jpg" ,"https://p2.ssl.qhimgs1.com/dr/578_774_/t015822699eef49a5cc.jpg" ,"北京朝阳" ,18 , 2,"个女人都是被种种牵绊舒服在滚滚红尘里，以哀伤的心，慢慢独到年华老去，终此一生。"));
        put(11, new UserBean(11, "拿稳我心你称王", "https://p.ssl.qhimg.com/t01aca9887854891929.jpg" ,"https://p3.ssl.qhimgs1.com/dr/578_382_/t01f5e0987e89922aa9.jpg" ,"湖北襄阳" ,19 , 2,"I didn't stop loving you.I just decided not to show my love."));
        put(12, new UserBean(12, "仗剑倚青天つ", "https://p.ssl.qhimg.com/t01fd7fe000944649c6.jpg" ,"https://p5.ssl.qhimgs1.com/dr/578_386_/t01b4f15741b4b7aa15.jpg" ,"湖北天门" ,22 , 2,"这个社会什么都可以是假的，但是，我唯一不能容忍的就是：钱的假的。"));
        put(13, new UserBean(13, "一生酷到底━═", "https://p.ssl.qhimg.com/t01942c71ff242289d9.jpg" ,"https://p5.ssl.qhimgs1.com/dr/578_578_/t016177097bcba1bcd5.jpg" ,"湖北宜昌" ,23 , 1,"心总是在最痛时，复苏；爱总是在最深时，落下帷幕。"));
        put(14, new UserBean(14, "宇宙第一帅胚", "https://p.ssl.qhimg.com/t01a797c1e165ea9f14.jpg" ,"https://p5.ssl.qhimgs1.com/dr/578_384_/t0169791ab581437fef.jpg" ,"湖南长沙" ,24 , 1,"挤公交是包含散打、瑜珈、柔道、平衡木等多种体育和健身项目于一体的综合性运动。"));
        put(15, new UserBean(15, "帅飞一条街", "https://p.ssl.qhimg.com/t01ecc074986ad1a0ba.jpg" ,"https://p1.ssl.qhimgs1.com/dr/578_432_/t017cfb90896b2f775b.jpg" ,"湖南岳阳" ,26 , 1,"原来，很多时候，最让人疼痛的，是孤独。"));
        put(16, new UserBean(16, "战神灵魂", "https://p.ssl.qhimg.com/t01a2f82c9aa97a63f4.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_384_/t01dedde1abb87fe64a.jpg" ,"武汉轻工大学" ,28 , 0,"女生应该骄傲的活着，而不是卑微的恋爱。"));
        put(17, new UserBean(17, "从头酷到脚", "https://p.ssl.qhimg.com/t017d10751189aca021.jpg" ,"https://p3.ssl.qhimgs1.com/dr/578_384_/t01fb0e60ff70513e0d.jpg" ,"湖北工业大学" ,21 , 2,"I'll smile before all people look at you, performance."));
        put(18, new UserBean(18, "傲视之巅", "https://p.ssl.qhimg.com/t0112a220bdb5fc5292.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_384_/t01d0160895cfa7e296.jpg" ,"湖北经济学院" ,31 , 0,"回忆千丝万缕，思念缱倦流年，昨日种种，红叶黄花，一恍如烟霞。"));
        put(19, new UserBean(19, "馭電追風雨", "https://p.ssl.qhimg.com/t0197152db76690b5a9.jpg" ,"https://p3.ssl.qhimgs1.com/dr/578_384_/t014749d906dcd5fad3.jpg" ,"上海交通大学" ,32 , 2,"喝白开水的人，不一定很纯。"));
        put(20, new UserBean(20, "酷到失控", "https://p.ssl.qhimg.com/t01f1c6faf2bebc07d2.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_384_/t018a8aab5a54da7fb4.jpg" ,"中国科学技术大学" ,55 , 0,"感情是没有公式，没有原则没有道理可循的，可是人们至死都还在执著与追求。"));
        put(21, new UserBean(21, "┌;饕餮噬天", "https://p.ssl.qhimg.com/t01020f0617455b8995.jpg" ,"https://p3.ssl.qhimgs1.com/dr/578_384_/t01a617f89cbc438eb9.jpg" ,"哈尔滨工业大学" ,54 , 1,"Love makes you hold on to things you wouldn’t have been able to."));
        put(22, new UserBean(22, "一秒メ速杀", "https://p.ssl.qhimg.com/t012d3983a0169afa90.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_868_/t0185dead6ea9dfd14e.jpg" ,"北京航空航天大学" ,46 , 0,"擦干眼泪，抬起头，莪依然拥有最坚强的笑容。"));
        put(23, new UserBean(23, "哥丶曾用C车称霸", "https://p.ssl.qhimg.com/t019928cb519504a8c5.jpg" ,"https://p5.ssl.qhimgs1.com/dr/578_864_/t01a881b34f659f4315.jpg" ,"河南信阳" ,37 , 2,"夏天就是不好，穷的时候我连西北风都没有喝。"));
        put(24, new UserBean(24, "去爱去疯去执着", "https://p.ssl.qhimg.com/t01a3225285cda30314.jpg" ,"https://p5.ssl.qhimgs1.com/dr/578_432_/t018c9d28095cb10e85.jpg" ,"河南漯河" ,45 , 0,"所有的失敗，與失去自己的失敗比起來，更是微不足道。"));
        put(25, new UserBean(25, "酒神", "https://p.ssl.qhimg.com/t01666b511f61006aa2.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_770_/t016b28f08d3007d974.jpg" ,"广西桂林" ,66 , 0,"她的忧伤，只为一些在颓废中荒废的年华。"));
        put(26, new UserBean(26, "哥↖超神了", "https://p.ssl.qhimg.com/t0168cd944ae26436ed.jpg" ,"https://p5.ssl.qhimgs1.com/dr/578_384_/t01d5c1d61d72247ff5.jpg" ,"广西阳朔" ,43 , 0,"有些人永远不会被遗忘，有些人永远只是代替品。"));
        put(27, new UserBean(27, "嗜血飞龙", "https://p.ssl.qhimg.com/t01be3b242d22d34c6e.jpg" ,"https://p2.ssl.qhimgs1.com/dr/578_384_/t0180dbeb7b2d466dec.jpg" ,"贵州贵阳" ,22 , 2,"爱情也许会随着季节的变迁而褪去，但友谊会为你全年守侯。"));
        put(28, new UserBean(28, "霹雳无敌", "https://p.ssl.qhimg.com/t010e57ddd763c3e814.jpg" ,"https://p1.ssl.qhimgs1.com/dr/578_374_/t0180efa16d4d1cba57.jpg" ,"四川成都" ,28 , 0,"感情是没有公式，没有原则没有道理可循的，可是人们至死都还在执著与追求。"));
        put(29, new UserBean(29, "独撑全场", "https://p.ssl.qhimg.com/t016b0e3d5920ce25f1.jpg" ,"https://p0.ssl.qhimgs1.com/dr/578_384_/t011b5790b23c936276.jpg" ,"浙江杭州" ,33 , 2,"怎样的一个冬天，我看见烟火幸福满天。又怎样的一个冬天，冷得让我泪流满面。"));
        put(30, new UserBean(30, "不朽神王", "https://p.ssl.qhimg.com/t017081c67288d79538.jpg" ,"https://p2.ssl.qhimgs1.com/dr/578_384_/t01e552cd0ad2e87e52.jpg" ,"安徽合肥" ,26 , 0,"我这人不懂音乐，所以时而不靠谱，时而不着调。"));
    }};

    // 视频数据
    public static SparseArray<VideoBean> VideoList = new SparseArray<VideoBean>(){{
        put(1, new VideoBean(1, "https://p26.douyinpic.com/tos-cn-p-0015/52a44cd0f20f4e47a02fc024b352abd0_1622205519~tplv-dy-360p.jpeg", "超级鸡马体验超高难度地狱模式，大黄崩溃了", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2oe863c77u6arm00r8g&ratio=720p&line=0"));
        put(2, new VideoBean(2, "https://p3.douyinpic.com/tos-cn-p-0015/f21cca9009db49fa8673231708170553_1622116507~tplv-dy-360p.jpeg", "超级鸡马基友互坑时刻，失去信心的大黄", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0200fg10000c2noh33c77u7rldvc80g&ratio=720p&line=0"));
        put(3, new VideoBean(3, "https://p3.douyinpic.com/tos-cn-p-0015/c0cb9f67fb7943a597324859f3c6a247_1622992747~tplv-dy-360p.jpeg", "迷你世界无限套娃合西瓜，伙伴捣乱抢生意，那我走？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2ueejbc77u55imc2kh0&ratio=720p&line=0"));
        put(4, new VideoBean(4, "https://p9.douyinpic.com/tos-cn-p-0015/d35df6920e304e1d89bc2da2a8542a63_1622992741~tplv-dy-360p.jpeg", "荒野求生出发雪山城堡，媳妇公主现身，名副其实三剑客", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2ueefjc77u7pgi7d2l0&ratio=720p&line=0"));
        put(5, new VideoBean(5, "https://p26.douyinpic.com/tos-cn-p-0015/8b44de38f0af413cad0309407d9bbb5a_1622910908~tplv-dy-360p.jpeg", "只只大冒险勇闯冰雪天敌，让我们荡起双桨，多米差点被红烧", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2tqfcbc77u7pghl11f0&ratio=720p&line=0"));
        put(6, new VideoBean(6, "https://p3.douyinpic.com/tos-cn-p-0015/d8b27728ee7c4307a0da0e5375cb6847_1622802859~tplv-dy-360p.jpeg", "迷你世界生存到极致就像开挂狼堡大升级！火柴盒秒变大别墅", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2t037bc77u99amfcrcg&ratio=720p&line=0"));
        put(7, new VideoBean(7, "https://p9.douyinpic.com/tos-cn-p-0015/f5ffbfac5855435db282a48c21ad86ef_1622802766~tplv-dy-360p.jpeg", "迷你世界流沙来了快建房，建造基岩房，善心大发搭救伙伴？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0200fg10000c2t02frc77ue9k8it51g&ratio=720p&line=0"));
        put(8, new VideoBean(8, "https://p26.douyinpic.com/tos-cn-p-0015/bc3b441d79634ed2be97bee37cccf0c5_1622740457~tplv-dy-360p.jpeg", "方舟生存进化神奇宝贝61水晶洞穴对抗死亡蠕虫，寻获团结神器", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2sgro3c77u6ark8buf0&ratio=720p&line=0"));
        put(9, new VideoBean(9, "https://p11.douyinpic.com/tos-cn-p-0015/b4af2f96eba441cda73390c395c11a9e_1610528747~tplv-dy-360p.jpeg", "2021年值得入住的民宿，有生之年一定要去“睡”一次！", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fbd0000bvvbf74f27p33ot7scog&ratio=720p&line=0"));
        put(10, new VideoBean(10, "https://p26.douyinpic.com/tos-cn-p-0015/5c135e0e11b24fb0a68753f3d687dea9_1597227392~tplv-dy-360p.jpeg", "国内最美的热气球景点，你们去过吗？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0200ffa0000bsps2f19dds8ickgdepg&ratio=720p&line=0"));
        put(11, new VideoBean(11, "https://p26.douyinpic.com/tos-cn-p-0015/c21f7fd21d64487da8aa42dce6a03a7f_1597137139~tplv-dy-360p.jpeg", "每个月最适合旅行你过的地方？你真的去对啦吗！", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0200f710000bsp60uhultt2lbn69dm0&ratio=720p&line=0"));
        put(12, new VideoBean(12, "https://p3.douyinpic.com/tos-cn-p-0015/321d3fe11da14debaa027f06be10c23c_1596453366~tplv-dy-360p.jpeg", "没有去过香格里拉，怎知世界的纯净，云南最值得打卡的地方！", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0200f540000bsjv3pn66jan1d2adivg&ratio=720p&line=0"));
        put(13, new VideoBean(13, "https://p11.douyinpic.com/tos-cn-p-0015/422abdf7c362450c8f7c9cb1e960d296_1602583642~tplv-dy-360p.jpeg", "西双版纳4天3晚游才人均不到一千？这份攻略你可要收好了！", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300f050000bu2nku3ircaev3kmjkbg&ratio=720p&line=0"));
        put(14, new VideoBean(14, "https://p5-ipv6.douyinpic.com/tos-cn-p-0015/be19f7ffbb484918bc887eeabc998884_1610510314~tplv-dy-360p.jpeg", "有种冰雪童话，叫冬天的哈尔滨！", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fe90000bvv6pvnbjpufrvum2u00&ratio=720p&line=0"));
        put(15, new VideoBean(15, "https://p5-ipv6.douyinpic.com/tos-cn-p-0015/1a99e8fd03bc460caf84aa907114fb1b_1621522703~tplv-dy-360p.jpeg", "iPad Pro 2021款12.9英寸黑白颜色对比。", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2j7h4l0296d5d3stmeg&ratio=720p&line=0"));
        put(16, new VideoBean(16, "https://p3.douyinpic.com/tos-cn-p-0015/1ef8c86241794397bab1dc2636829f28_1610682052~tplv-dy-360p.jpeg", "想要主动降噪与好音质，2021年适合iPhone的真无线蓝牙耳机怎么选？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300f620000c00grqvh77phu82ph2vg&ratio=720p&line=0"));
        put(17, new VideoBean(17, "https://p9.douyinpic.com/tos-cn-p-0015/6947ee2833d64c759781347bb9493b51_1621682660~tplv-dy-360p.jpeg", "首个进入太空的生物，为什么要选择一只流浪狗呢？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0d00fg10000c2kejkjfgt7pa5plj1v0&ratio=720p&line=0"));
        put(18, new VideoBean(18, "https://p29.douyinpic.com/tos-cn-p-0015/b9f343aca10d42a3b3f2812d844975e6_1621508159~tplv-dy-360p.jpeg", "同样是鲤鱼,为什么黄河鲤鱼更贵呢？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0d00fg10000c2j40anc4lr2q92q4khg&ratio=720p&line=0"));
        put(19, new VideoBean(19, "https://p9.douyinpic.com/tos-cn-p-0015/88478c2b62ef4a9698646c1498db03ce_1621338097~tplv-dy-360p.jpeg", "肉被切开后,为什么表面会跳动呢？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2hqfo56j906d0kh5mtg&ratio=720p&line=0"));
        put(20, new VideoBean(20, "https://p3.douyinpic.com/tos-cn-p-0015/282e28eca8764b1ca63f2d047d83c0bf_1620819728~tplv-dy-360p.jpeg", "为什么椰子里面会有水呢？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0d00fg10000c2drtt8gk4jgd7f6iv70&ratio=720p&line=0"));
        put(21, new VideoBean(21, "https://p3.douyinpic.com/tos-cn-p-0015/a3b08e5aafba4fb68dec2787c3106d66_1620644241~tplv-dy-360p.jpeg", "为什么工人要往水泥里加白糖呢？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2ch2offq9lkha32kbhg&ratio=720p&line=0"));
        put(22, new VideoBean(22, "https://p3.douyinpic.com/tos-cn-p-0015/27c47da4df52490c8cb7a88e87e3c1a1_1621074960~tplv-dy-360p.jpeg", "科学家为什么不敢把火星土壤带回地球呢？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c2fq7akpeg7h5b65tt7g&ratio=720p&line=0"));
        put(23, new VideoBean(23, "https://p9.douyinpic.com/tos-cn-p-0015/d0fa69ee574a484890c21a9319d17945_1621335837~tplv-dy-360p.jpeg", "有气囊的鼻托你见过吗？", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0d00fg10000c2hpt2pd3u0ebthcfs00&ratio=720p&line=0"));
        put(24, new VideoBean(24, "https://p3.douyinpic.com/tos-cn-p-0015/feb6883763e14f70a0c1fd72db8bb0a1_1578713534~tplv-dy-360p.jpeg", "我做的红酒雪梨，每次上桌不到1分钟就被抢光，思虑再三，决定把方法发出来，希望学会的能给我个❤️", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0200f3b0000bock3dfff77e7cs0sg20&ratio=720p&line=0"));
        put(25, new VideoBean(25, "https://p9.douyinpic.com/tos-cn-p-0015/ebe5f77db8ec4be2a543d0a4930940cc_1619255418~tplv-dy-360p.jpeg", "的新吃法！真的太好吃了！全麦欧包更适合做主食哦！", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0d00fg10000c21u0m6e1f8bno2t4g0g&ratio=720p&line=0"));
        put(26, new VideoBean(26, "https://p26.douyinpic.com/tos-cn-p-0015/4194debff77a46809f820df7aa576945_1618909651~tplv-dy-360p.jpeg", "月旨期放心吃，吃肉肉掉肉肉", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c1v9jih8v0llc8ll1nl0&ratio=720p&line=0"));
        put(27, new VideoBean(27, "https://p5-ipv6.douyinpic.com/tos-cn-p-0015/5af33941f597475e9384484699b5384c_1618734474~tplv-dy-360p.jpeg", "用茶代替水做出来的红烧肉，肥而不腻，隔壁老王都说好吃！", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fg10000c1tuqn7764jl2usnm9vg&ratio=720p&line=0"));
        put(28, new VideoBean(28, "https://p5-ipv6.douyinpic.com/29b0a000063e8b0c4b4b5~tplv-dy-360p.jpeg", "塞尔达传说来到王城遗址，塞尔达公主为我举行授勋仪式", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300f2a0000bkg294tj482rnh8bi410&ratio=720p&line=0"));
        put(29, new VideoBean(29, "https://p26.douyinpic.com/29428000b866180a60e1c~tplv-dy-360p.jpeg", "塞尔达传说用火把点燃哈特诺之塔，我得到了四大部落的位置", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300f880000bke1osdmuq8m6dopcfg0&ratio=720p&line=0"));
        put(30, new VideoBean(30, "https://p3.douyinpic.com/27451000a879e0cd3b3d3~tplv-dy-360p.jpeg", "翼龙历险记打败会喷火的龙皇后，5只小翼龙宝宝出生了", "https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300fb70000bk1gdqmt2ulrrokagfg0&ratio=720p&line=0"));
    }};

    // 音乐数据
    public static SparseArray<MusicBean> MusicList = new SparseArray<MusicBean>(){{
        put(1, new MusicBean(1,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/258d5c278eb0418ea2ea362c21403e27.jpeg", "孤勇者 - 陈奕迅"));
        put(2, new MusicBean(2,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/78085574025c4d3cb66009647c379492.jpeg", "Shadow of the Sun - Max Elto"));
        put(3, new MusicBean(3,"https://p6.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/756143d75cb04a6e8d280538d9fcbe73.jpeg", "守护着我的光 - 李巍V仔"));
        put(4, new MusicBean(4,"https://p9.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/b7528a0aa0164f2aadfe332b2e54ca76.jpeg", "WAVE（Prod by 张杰峻） - 花欲燃"));
        put(5, new MusicBean(5,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/5614d4be2fe5475faebad7865050df83.jpeg", "陪你度过漫长岁月 - 张钰琪"));
        put(6, new MusicBean(6,"https://p11.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/cbac750dc48b4e8e8f9a88ce8813e49e.jpeg", "哪里都是你（DJR7版） - DJR7"));
        put(7, new MusicBean(7,"https://p6.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/6267a87bdc7044949f59eeb624600606.jpeg", "It's a Beautiful Day - Evan McHugh"));
        put(8, new MusicBean(8,"https://p9.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/d67d7f6ff98644b58718a6757147b4af.jpeg", "人世间（电视剧《人世间》主题曲） - 雷佳"));
        put(9, new MusicBean(9,"https://p6.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/08d50473149248ec9d29419e88fe9fc0.jpeg", "爱丫爱丫 - By2"));
        put(10, new MusicBean(10,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/2757047296424a679cca6c017ec1eba0.jpeg", "在草地上肆意奔跑(片段) - 傅如乔"));
        put(11, new MusicBean(11,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/23960b32a6b149e3b336b4ecd433246f.jpeg", "落在生命里的光 - 尹昔眠"));
        put(12, new MusicBean(12,"https://p6.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/869bd0664feb47e8aa256d090a06d8b6.jpeg", "渐暖片段1 - 时代少年团"));
        put(13, new MusicBean(13,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/c44420cfb0d64f6b9516a0b9c41bd49a.jpeg", "行于水 （剪辑版） - 万妮达Vinida Weng"));
        put(14, new MusicBean(14,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/70d2e6e3921b42f68265dcd02ecc0b3d.jpeg", "裹着心的光 30s - 林俊杰"));
        put(15, new MusicBean(15,"https://p9.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/3d3cb370ddb24e95b54ab9bd58c40872.jpeg", "Something Just Like This - Bely Basarte"));
        put(16, new MusicBean(16,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/984c581341be494ab12e9195d8b9dd60.jpeg", "张碧晨《光的方向》（剪辑版） - 张碧晨"));
        put(17, new MusicBean(17,"https://p11.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/77790e0f66f64fb88934bf022f34c1dc.jpeg", "致00后的预防针 - 王以诺"));
        put(18, new MusicBean(18,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/7677e49b6a734b3bb2db8cc357351f5c.jpeg", "雪龙吟（剪辑版） - 张杰"));
        put(19, new MusicBean(19,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/b4c665dbf7234ebfa59e1cec6a5d9dbc.jpeg", "我们的爱 - 曹雨航"));
        put(20, new MusicBean(20,"https://p9.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/c8db6cd7a66b402f854bb547b757def0.jpeg", "老茧-剪辑版 - 简弘亦"));
        put(21, new MusicBean(21,"https://p6.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/cf8b3f3801924f049da1db34286572aa.jpeg", "Beautiful - INTO1-米卡"));
        put(22, new MusicBean(22,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/1cc8823cd51e4ee4b06b5ec65616afeb.jpeg", "天若有情 ((电视剧「锦绣未央」主题曲) - A-Lin"));
        put(23, new MusicBean(23,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/cd7d189e43cb4c59ac081a60571220ec.jpeg", "DO WHATEVER想做就做（剪辑版） - 卡西恩Cacien"));
        put(24, new MusicBean(24,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/62756bbdce8d46c9be06cac90813425e.jpeg", "平凡的一天 - 毛不易"));
        put(25, new MusicBean(25,"https://p9.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/cd0510692553442b80ca8006d6f326cd.jpeg", "我们啊（男版）片段2 - 三块木头"));
        put(26, new MusicBean(26,"https://p26.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/02011d6377e64bfcb35820795c6560cf.jpeg", "剑魂_鱼多余完整版已上线 - 鱼多余"));
        put(27, new MusicBean(27,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/c5dfad8371c54cd69986c9dc7a4bb325.jpeg", "最好的都给你（剪辑版） - 余佳运"));
        put(28, new MusicBean(28,"https://p11.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/1f5f7b1b48f0427fab2d28cfe9e3c009.jpeg", "微风吹（剪辑版） - 李润祺"));
        put(29, new MusicBean(29,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/5064ec1dc0d845419fea14ce792bed70.jpeg", "Celebrity - IU"));
        put(30, new MusicBean(30,"https://p3.douyinpic.com/aweme/200x200/tos-cn-v-2774c002/23960b32a6b149e3b336b4ecd433246f.jpeg", "落在生命里的光 - 尹昔眠"));
    }};

    // 评论数据
    public static SparseArray<CommentBean> CommentList = new SparseArray<CommentBean>(){{
        put(1, new CommentBean(1, UserList.get(1), "超级鸡马体验超高难度地狱模式，大黄崩溃了"));
        put(2, new CommentBean(2, UserList.get(2), "超级鸡马基友互坑时刻，失去信心的大黄"));
        put(3, new CommentBean(3, UserList.get(3), "迷你世界无限套娃合西瓜，伙伴捣乱抢生意，那我走？"));
        put(4, new CommentBean(4, UserList.get(4), "荒野求生出发雪山城堡，媳妇公主现身，名副其实三剑客"));
        put(5, new CommentBean(5, UserList.get(5), "只只大冒险勇闯冰雪天敌，让我们荡起双桨，多米差点被红烧"));
        put(6, new CommentBean(6, UserList.get(6), "迷你世界生存到极致就像开挂狼堡大升级！火柴盒秒变大别墅"));
        put(7, new CommentBean(7, UserList.get(7), "迷你世界流沙来了快建房，建造基岩房，善心大发搭救伙伴？"));
        put(8, new CommentBean(8, UserList.get(8), "方舟生存进化神奇宝贝61水晶洞穴对抗死亡蠕虫，寻获团结神器"));
        put(9, new CommentBean(9, UserList.get(9), "2021年值得入住的民宿，有生之年一定要去“睡”一次！"));
        put(10, new CommentBean(10, UserList.get(10), "国内最美的热气球景点，你们去过吗？"));
        put(11, new CommentBean(11, UserList.get(11), "每个月最适合旅行你过的地方？你真的去对啦吗！"));
        put(12, new CommentBean(12, UserList.get(12), "没有去过香格里拉，怎知世界的纯净，云南最值得打卡的地方！"));
        put(13, new CommentBean(13, UserList.get(13), "西双版纳4天3晚游才人均不到一千？这份攻略你可要收好了！"));
        put(14, new CommentBean(14, UserList.get(14), "有种冰雪童话，叫冬天的哈尔滨！"));
        put(15, new CommentBean(15, UserList.get(15), "iPad Pro 2021款12.9英寸黑白颜色对比。"));
        put(16, new CommentBean(16, UserList.get(16), "想要主动降噪与好音质，2021年适合iPhone的真无线蓝牙耳机怎么选？"));
        put(17, new CommentBean(17, UserList.get(17), "首个进入太空的生物，为什么要选择一只流浪狗呢？"));
        put(18, new CommentBean(18, UserList.get(18), "同样是鲤鱼,为什么黄河鲤鱼更贵呢？"));
        put(19, new CommentBean(19, UserList.get(19), "肉被切开后,为什么表面会跳动呢？"));
        put(20, new CommentBean(20, UserList.get(20), "为什么椰子里面会有水呢？"));
        put(21, new CommentBean(21, UserList.get(21), "为什么工人要往水泥里加白糖呢？"));
        put(22, new CommentBean(22, UserList.get(22), "科学家为什么不敢把火星土壤带回地球呢？"));
        put(23, new CommentBean(23, UserList.get(23), "有气囊的鼻托你见过吗？"));
        put(24, new CommentBean(24, UserList.get(24), "我做的红酒雪梨，每次上桌不到1分钟就被抢光，思虑再三，决定把方法发出来，希望学会的能给我个❤️"));
        put(25, new CommentBean(25, UserList.get(25), "的新吃法！真的太好吃了！全麦欧包更适合做主食哦！"));
        put(26, new CommentBean(26, UserList.get(26), "月旨期放心吃，吃肉肉掉肉肉"));
        put(27, new CommentBean(27, UserList.get(27), "用茶代替水做出来的红烧肉，肥而不腻，隔壁老王都说好吃！"));
        put(28, new CommentBean(28, UserList.get(28), "塞尔达传说来到王城遗址，塞尔达公主为我举行授勋仪式"));
        put(29, new CommentBean(29, UserList.get(29), "塞尔达传说用火把点燃哈特诺之塔，我得到了四大部落的位置"));
        put(30, new CommentBean(30, UserList.get(30), "翼龙历险记打败会喷火的龙皇后，5只小翼龙宝宝出生了"));
    }};

    // 随机用户视频
    public static SparseArray<List<VideoBean>> UserVideo = new SparseArray<List<VideoBean>>(){{
        Random rand = new Random();
        for(int i = 0; i < UserList.size(); i++) {
            int key = UserList.keyAt(i);
            List<VideoBean> list = new ArrayList<>();
            int getI = rand.nextInt(10);
            int getS = rand.nextInt(20) + 10;
            for(int o = getI; o < getS; o++) {
                list.add(VideoList.get(VideoList.keyAt(o)));
            }
            put(key, list);
        }
    }};

    public static class UserBean {
        public int id;
        public String name;
        public String icon;
        public String bg;
        public String city;
        public int age;
        public int sex;
        public String text;
        public int praise;
        public int follow;
        public int fans;
        public boolean isFollow = false;
        UserBean(int id, String name, String icon, String bg, String city, int age, int sex, String text) {
            Random rand = new Random();
            this.id = id;
            this.name = name;
            this.icon = icon;
            this.bg = bg;
            this.city = city;
            this.age = age;
            this.sex = sex;
            this.text = text;
            praise = rand.nextInt(100000);
            follow = rand.nextInt(100000);
            fans = rand.nextInt(100000);
        }
        public String getPraise() {
            return Common.ParseNum(praise);
        }
        public String getFollow() {
            return Common.ParseNum(follow);
        }
        public String getFans() {
            return Common.ParseNum(fans);
        }
    }

    public static class VideoBean {
        public int id;
        public String img;
        public String text;
        public String src;
        public int favCount;
        public int sayCount;
        public int shareCount;
        public boolean isFav = false;
        VideoBean(int id, String img, String text, String src) {
            Random rand = new Random();
            this.id = id;
            this.img = img;
            this.text = text;
            this.src = src;
            favCount = rand.nextInt(100000);
            sayCount = rand.nextInt(100000);
            shareCount = rand.nextInt(100000);
        }
        public String getFavCount() {
            return Common.ParseNum(favCount);
        }
        public String getSayCount() {
            return Common.ParseNum(sayCount);
        }
        public String getShareCount() {
            return Common.ParseNum(shareCount);
        }
    }

    public static class MusicBean {
        public int id;
        public String icon;
        public String text;
        MusicBean(int id, String icon, String text) {
            this.id = id;
            this.icon = icon;
            this.text = text;
        }
    }

    public static class CommentBean {
        public int id;
        public UserBean user;
        public String text;
        public String time;
        public int favCount;
        CommentBean(int id, UserBean user, String text) {
            Random rand = new Random();
            this.id = id;
            this.user = user;
            this.text = text;
            this.time = (rand.nextInt(59) + 1) + (rand.nextInt(2) == 0 ? "小时" : "分钟") + "前";
            favCount = rand.nextInt(100000);
        }
        public String getFavCount() {
            return Common.ParseNum(favCount);
        }
    }

}
