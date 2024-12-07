# **RasaNusa: Fine-Tuned Deep Learning Architectures with ImageNetV2 for Image Classification, Attention Mechanism, and Residual Connections**
<div align="center">
  <img src="https://github.com/rizkifrtma11/Capstone-Project-Bangkit-2024-batch-2/blob/c1f1ae548e1bf70ba2f719636131f849ded07071/Machine-Learning/image-assets/logo.jpg?raw=true" alt="Deskripsi gambar" width="400" height="400">
</div>

**RasaNusa** is an image classification project designed to classify various images into predefined categories. The model is built using advanced deep learning techniques, including **fine-tuning** of a pre-trained **MobileNetV2** model, to enhance the accuracy and performance of the classification process. Specifically, the project leverages an **Attention Mechanism** and **Residual Connections** integrated with MobileNetV2, allowing it to effectively extract features from images while addressing issues like overfitting and focusing on relevant visual information. This approach enables the model to adapt to the specific dataset, improving its ability to classify images accurately across diverse categories.

## **Model Architecture Overview**

The architecture of this model is a combination of **Transfer Learning**, **Attention Mechanisms**, **Residual Connections**, and **Fine-Tuning** on **ImageNetV2**, designed to enhance the model's ability to classify images accurately and efficiently while adapting to the specific dataset. Here's a of the architecture:

1. **Base Model: MobileNetV2 (Pre-trained on ImageNetV2)**

2. **Attention Mechanism**

3. **Residual Connections**

4. **Multi-Branch Architecture**  

5. **Fully Connected Layers and Output**

## **Package Installation**

| Package       | Version  |
|---------------|----------|
| pydot         | 3.0.3    |
| graphviz      | 0.20.3   |
| tensorflow    | 2.15.0   |
| scikit-learn  | 1.5.2    |
| matplotlib    | 3.8.0    |
| seaborn       | 0.13.2   |
| Pillow        | 11.0.0   |
| pandas        | 2.2.2    |
| numpy         | 1.26.4   |
| ipywidgets    | 8.1.5    |

## **Class Distribution Dataset**
<div align="center">
  <img src="https://github.com/rizkifrtma11/Capstone-Project-Bangkit-2024-batch-2/blob/main/Machine-Learning/image-assets/distribution.png?raw=true" alt="Distribution">
</div>

## **Sample Train Dataset**
<div align="center">
  <img src="https://github.com/rizkifrtma11/Capstone-Project-Bangkit-2024-batch-2/blob/main/Machine-Learning/image-assets/sample_train_dataset.png?raw=true" alt="Sample Train Dataset">
</div>

## **Sample Validation Dataset**
<div align="center">
  <img src="https://github.com/rizkifrtma11/Capstone-Project-Bangkit-2024-batch-2/blob/main/Machine-Learning/image-assets/sample_validation_dataset.png?raw=true" alt="Sample Validation Dataset">
</div>

## **Metric Accuracy (Train Accuracy : 98%, Validation Accuracy : 94%)**
<div align="center">
  <img src="https://github.com/rizkifrtma11/Capstone-Project-Bangkit-2024-batch-2/blob/main/Machine-Learning/image-assets/metric_accuracy.png?raw=true" alt="Metric Accuracy">
</div>

## **Metric Loss (Train Loss : 3%, Validation Loss : 20%)**
<div align="center">
  <img src="https://github.com/rizkifrtma11/Capstone-Project-Bangkit-2024-batch-2/blob/main/Machine-Learning/image-assets/metric_loss.png?raw=true" alt="Metric Loss">
</div>

<h1>Source Datasets</h1>
<h3>Source Datasets for Classification Model :</h3>
<br>
<ol>
<li>Gambar train/valid/test 01 - 09 : https://universe.roboflow.com/bangkit/indonesian-food-pedsx/dataset/1</li>
<li>es_pisang_ijo : https://sosialita.tanahlautkab.go.id/assets/uploads/webp/fotoproduk/crop/79HxPQNo20230227122619.jpeg.webp</li>
<li>es_pisang_ijo_1 : https://img.sokoguru.id/backend/1645284147968610304/segarnya-es-pisang-ijo-untuk-takjil-buka-puasa-resep-pisang-ijo-asli-makassar.webp</li>
<li>es_pisang_ijo_2 : https://suaraaisyiyah.id/wp-content/uploads/2021/04/es-pisang-ijo-resepmasakancom-1acdd3b7e61c7115c6c2c70ab80980ac.jpg</li>
<li>es_pisang_ijo_3 :https://asset.kompas.com/crops/IgNXDxizAxLs_pkvZsfGz0WL6iY=/1x0:1000x666/375x240/data/photo/2022/04/03/6248ebf53ef0c.jpeg</li>
<li>es_pisang_ijo_4 : https://cdn.antaranews.com/cache/800x533/2021/08/15/pisang-ijo.jpg</li>
<li>es_pisang_ijo_5 : https://celebrithink.com/wp-content/uploads/2024/03/es-pisang-ijo-foto-resep-utama.jpg</li>
<li>es_pisang_ijo_6 : https://disbudpar.sulselprov.go.id/uploads/wisata/es%20ijo.jpg</li>
<li>es_pisang_ijo_7 : https://assets-a2.kompasiana.com/items/album/2020/05/11/whatsapp-image-2020-05-11-at-20-51-23-5eb95da9097f362ce36a6433.jpeg?v=770</li>
<li>es_pisang_ijo_8 : https://www.agronet.co.id/files/media/news/images/645x372/-_230216182611-786.jpg</li>
<li>es_pisang_ijo_9 : https://img.inews.co.id/media/1050/files/inews_new/2018/11/16/es_pisangijo.jpg</li>
<li>es_pisang_ijo_10 : https://statik.tempo.co/data/2024/03/28/id_1291221/1291221_720.jpg</li>
<li>es_pisang_ijo_11 : https://img-global.cpcdn.com/recipes/b43e0471ef94d368/1360x964cq70/es-pisang-ijo-foto-resep-utama.webp</li>
<li>es_pisang_ijo_12 : https://asset.kompas.com/crops/th5b3kbhJmGZdk5cPaIhw5umDlY=/32x0:1000x645/1200x800/data/photo/2022/12/21/63a2996e8af8d.jpg</li>
<li>es_pisang_ijo_13 : https://eventkampus.com/data/artikel/2/cara-membuat-es-pisang-ijo.jpeg</li>
<li>es_pisang_ijo_14 : https://imgx.parapuan.co/crop/166x90:1169x758/x/photo/2024/03/15/tips-membuat-es-pisang-ijo-takj-20240315060243.jpg</li>
<li>es_pisang_ijo_15 : https://asset-2.tstatic.net/palu/foto/bank/images/kudapan-takjil-es-pisang-ijo.jpg</li>
<li>es_pisang_ijo_16 : https://imgx.parapuan.co/crop/0x0:0x0/x/photo/2023/03/03/tips-memasak-cepat-es-pisang-ijo-20230303093451.jpg</li>
<li>es_pisang_ijo_17 : https://www.actasurya.com/wp-content/uploads/2018/12/WhatsApp-Image-2018-12-23-at-00.59.51-1024x768.jpeg</li>
<li>es_pisang_ijo_18 : https://c8.alamy.com/comp/T37JPF/es-pisang-ijo-traditional-iced-dessert-of-plantain-banana-roll-with-coconut-milk-custard-from-makassar-ujung-pandang-south-sulawesi-T37JPF.jpg</li>
<li>es_pisang_ijo_19 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-KrDu1Gsb0Cn0sFVsDL2JlnHdUWOEt5r30g&s</li>
<li>es_pisang_ijo_20 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXEB-WRd2XuTcDcqwlHpJOsFaq36gAQ6OoKQ&s</li>
<li>es_pisang_ijo_21 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_kfqjJTopMwtC6q94QyrXOYcLx0gDiVRDxA&s</li>
<li>es_pisang_ijo_22 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8oY2RgZH5wQCT5UHawzo3KEd4OKwyAoVLKw&s</li>
<li>es_pisang_ijo_23 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxD0Fr9t8lz6JlLgsV9GlZ90R629vvumTbnQ&s</li>
<li>es_pisang_ijo_24 : https://asset-2.tstatic.net/ternate/foto/bank/images/es-pisang-ijo.jpg</li>
<li>es_pisang_ijo_25 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSysw805ynISqsdwHTYml4FhFzFGSMkmpYQRg&s</li>
<li>es_pisang_ijo_26 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ461POxQ38-oIy9qex_WW5LrH0-ncAkjwy7w&s</li>
<li>es_pisang_ijo_27 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZG3sWaroiJMJBAVt4B1F-ljJALREtwQ_W_g&s</li>
<li>es_pisang_ijo_28 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpRG-erkcAUSOtDxm5JBnmvca-M8AB9ixeyQ&s</li>
<li>es_pisang_ijo_29 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcrpVlgSki6eqscl_ymuZ2j1wg1ClmhPlvLQ&s</li>
<li>es_pisang_ijo_30 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDy1BKsz93muf_G_RXA__TRV5kmlK2KIt2Fw&s</li>
<li>es_pisang_ijo_31 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDxJ336orw4I7zs0b_hiyI728GThCYpkT1hA&s</li>
<li>es_pisang_ijo_32 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfvg8cB9TUHfsrbzmPd_t9INur41WMjU_lbw&s</li>
<li>es_pisang_ijo_33 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKwTLKrEbuwXF8gmp2ToD75MluMNwRI8ixig&s</li>
<li>es_pisang_ijo_34 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTh2AQpmGezd8AUu0mPrB2VcIf_BFOnlnpXXg&</li>
<li>es_pisang_ijo_35 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8lsoedgpF8tWPawkqqFzb5z34wimQDbNaqw&s</li>
<li>es_pisang_ijo_36 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQuc5JtEKD8ujkLYynFtgyxuZkC8ZhyCRtzbA&s</li>
<li>es_pisang_ijo_37 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3l0CPp8nJTdgIpN1LcNh3vbmCln2r813X6w&s</li>
<li>es_pisang_ijo_38 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTR668a8dT08ylja3wHjru7RcKMIQhsbbqTNg&s</li>
<li>es_pisang_ijo_39 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMjcBwLLYJ7mQLtk7FriIocJ7gEWt5pfdP8w&s</li>
<li>es_pisang_ijo_40 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYba1vruyM_AC4DnXf7Ht38BxFvflmIKagMQ&s</li>
<li>es_pisang_ijo_41 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREkWb7AEZAhXM_48phEjcPrHxbb6ljIkh6dg&s</li>
<li>es_pisang_ijo_42 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQj2qcFCRvpQsQbe9siycdkju73XdLuoyXnIA&s</li>
<li>es_pisang_ijo_43 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvExeFPdMY4VC_gs_4LPjroyE0UQRIZlRs1g&s</li>
<li>es_pisang_ijo_44 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgKeYETND5AbdYw7DGORz-faVcWS8JcOHeNg&s</li>
<li>es_pisang_ijo_45 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3l0CPp8nJTdgIpN1LcNh3vbmCln2r813X6w&s</li>
<li>es_pisang_ijo_46 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMjcBwLLYJ7mQLtk7FriIocJ7gEWt5pfdP8w&s</li>
<li>es_pisang_ijo_47 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYba1vruyM_AC4DnXf7Ht38BxFvflmIKagMQ&s</li>
<li>es_pisang_ijo_48 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS45bDm4bRrQ9UF9xY3kA9mfl3MoDK5LTSD7Q&s</li>
<li>es_pisang_ijo_49 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvExeFPdMY4VC_gs_4LPjroyE0UQRIZlRs1g&s</li>
<li>es_pisang_ijo_50 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYgfkbQVcoZ78U5XheWAL_2LFaJulqpFRP9g&s</li>
<li>es_pisang_ijo_51 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDa-koWgpiipaGIrKc07j76-DcIFNXA4030w&s</li>
<li>es_pisang_ijo_52 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqXsox_ONn3Ab4Sed95jOQ401T5cNaqXMoSg&s</li>
<li>es_pisang_ijo_53 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT01u5bbATlcoUukDMeG_3KK9RoQhf07B4ViQ&s</li>
<li>es_pisang_ijo_54 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_kQ9q_pdtFLFT3b5Le_FFwr0hR02vSMq0gw&s</li>
<li>es_pisang_ijo_55 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0iqHym1qKBMCVkeg7noBuYre-xbA-JFRidg&s</li>
<li>es_pisang_ijo_56 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuIXuLSfeJmDA4ch7aCA4sY3rS30sjHIDk4g&s</li>
<li>es_pisang_ijo_57 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQMeKoGTHQtvvur_CeK7u4YGSt_qplv85zsZw&s</li>
<li>es_pisang_ijo_58 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQb9F_g5Qyjjb20GOv_KZjt_OArfgEeG67Kew&s</li>
<li>es_pisang_ijo_59 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpvn2P7bNGGDHj8rbC44mtluEZqR_9eHFNSA&s</li>
<li>lumpia-1 : https://awsimages.detik.net.id/community/media/visual/2022/03/04/lumpia-semarang_43.jpeg?w=1200</li>
<li>lumpia-2 : https://asset.kompas.com/crops/UxROXDbwKoZ9Vr57w4sc0qHVfnM=/59x38:939x625/1200x800/data/photo/2022/05/05/62733af9841d8.jpg</li>
<li>lumpia-3 : https://inikotasemarang.com/wp-content/uploads/2017/12/Lumpia-Semarang.jpg</li>
<li>lumpia-4 : https://img.kurio.network/euOYZvBvKpnkbsV1vQvUvpmpDxI=/1200x1200/filters:quality(80)/https://kurio-img.kurioapps.com/20/10/13/29a1a2bc-7a57-4997-8121-7c2ce9855a05.jpeg</li>
<li>lumpia-5 : https://cdn-2.tstatic.net/tribunnews/foto/bank/images/lumpia-semarang-1.jpg</li>
<li>lumpia-6 : https://asset.kompas.com/crops/YH-mQzv3bPuNSvCUfdCIUaBI1q0=/91x63:971x649/1200x800/data/photo/2022/05/05/62733ab5cdfbf.jpg</li>
<li>lumpia-7 : https://imgcdn.espos.id/@espos/images/2022/06/lumpia-1.jpg</li>
<li>lumpia-8 : https://radarpena.disway.id/upload/5963cbda9d5c79a4a59774ef82a11a98.jpg</li>
<li>lumpia-9 : https://cdn0-production-images-kly.akamaized.net/bVMH96OuejxtNDYRbciLqvmt3EI=/1200x1200/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/2383426/original/008620400_1539580557-lumpia_ganesh2013.jpg</li>
<li>lumpia-10 : https://akcdn.detik.net.id/api/wm/2020/03/18/12dd9105-4a0d-4c1e-bb3a-6ec36015fcbf_169.jpeg?w=650</li>
<li>lumpia-11 : https://cdn1-production-images-kly.akamaized.net/5V0gOWkJ-YLDbQo0JkjuNGWNG9s=/0x40:999x603/1200x675/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/3528367/original/071758400_1627892861-shutterstock_114031060.jpg</li>
<li>lumpia-12 : https://cdn.idntimes.com/content-images/community/2023/05/img-20230526-185845-5e7f09505567688e0cb6154ede497009-b0409eac3b62081cfcabab9cb1b53fd9_600x400.jpg</li>
<li>lumpia-13 : https://asset-a.grid.id/crop/0x0:0x0/780x800/photo/bobofoto/original/1134_lumpia-khas-semarang-foto-priyo-wibowo.jpg</li>
<li>lumpia-14 : https://asset-2.tstatic.net/medan/foto/bank/images/resep-lumpia-khas-semarang-enak.jpg</li>
<li>lumpia-15 : https://img.okezone.com/content/2023/10/26/301/2908389/menguak-sejarah-lumpia-khas-semarang-ini-6-lumpia-semarang-terpopuler-eeS460wClO.JPG</li>
<li>lumpia-16 : https://cdn1-production-images-kly.akamaized.net/k7dsQ25l1eaZAM9G98bZ5Gp1Qxs=/1200x675/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/17002/original/plumpia.jpg</li>
<li>lumpia-17 : https://asset-2.tstatic.net/tribunjatengtravel/foto/bank/images/lumpia-semarang.jpg</li>
<li>lumpia-18 : https://st2.depositphotos.com/52939322/50675/i/1600/depositphotos_506753830-stock-photo-lumpia-lunpia-traditional-snack-semarang.jpg</li>
<li>lumpia-19 : https://media.suara.com/pictures/653x366/2022/11/22/34747-ilustrasi-lumpia-pixabaygenshes2013.jpg</li>
<li>lumpia-20 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTRbIha8B7lZgtvJuwjVDCT1Zf4dJ2cwYTwg&s</li>
<li>lumpia-21 : https://images.tokopedia.net/img/cache/700/product-1/2020/9/2/114184066/114184066_add3d8a6-414e-45e0-9b1f-8ca302752265_678_678.jpg</li>
<li>lumpia-22 : https://blue.kumparan.com/image/upload/fl_progressive,fl_lossy,c_fill,q_auto:best,w_640/v1634025439/01j9zdgee2szv54kms13t5kn5x.jpg</li>
<li>lumpia-23 : https://upload.wikimedia.org/wikipedia/commons/4/43/Loenpia_Semarang.JPG</li>
<li>lumpia-24 : https://images.tokopedia.net/img/cache/700/VqbcmM/2023/3/10/1af96407-d44f-476d-993b-56d68e0e5e6d.jpg</li>
<li>lumpia-25 : https://img-global.cpcdn.com/recipes/5c617bef2c6cf2ec/1200x630cq70/photo.jpg</li>
<li>lumpia-26 : https://bloktuban.com/files/source/2023/07/lumpia%20semarang%20dapur%20bunda%20daisy%20at%20cookpad.jpg</li>
<li>lumpia-27 : https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full//catalog-image/89/MTA-142504007/no_brand_lumpia_semarang_besek_full01_7d297d2b.jpg</li>
<li>lumpia-28 : https://jalanjalanyuk.co.id/wp-content/uploads/2024/04/makanan-khas-semarang.jpg</li>
<li>lumpia-29 : https://2.bp.blogspot.com/-Siw4LuJ1eys/TxQrpIecjyI/AAAAAAAAB-g/NBm5SAp9fAc/s500/lumpia-semarang1+y.jpg</li>
<li>lumpia-30 : https://img-global.cpcdn.com/recipes/57648494a9606c2a/680x482cq70/lumpia-semarang-dijamin-paling-gampang-dan-enaaaak-foto-resep-utama.jpg</li>
<li>lumpia-31 : https://i0.wp.com/jnewsonline.com/wp-content/uploads/2024/07/lumpia-semarang.jpg?resize=1000%2C600&ssl=1</li>
<li>lumpia-32 : https://awsimages.detik.net.id/api/wm/2020/05/23/cb428ebb-f3d6-4aff-91f0-99df3be0c894_169.jpeg?wid=54&w=650&v=1&t=jpeg</li>
<li>lumpia-33 : https://assets-pergikuliner.com/FBMOIqj7kldXYditSQRy3Kl8cHg=/fit-in/1366x768/smart/filters:no_upscale()/https://assets-pergikuliner.com/uploads/image/picture/3062789/picture-1696822882.jpg</li>
<li>lumpia-34 : https://i.ytimg.com/vi/CGy5_4UfvPE/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLCVHqHTcocjpQrV5d0M8UBNQCDqzg</li>
<li>lumpia-35 : https://ik.trn.asia/uploads/2019/01/306-piyya-piyya-lumpia-semarang-makanan-khas-kota-semarang-yang-nikmat-sampai-gigitan-terakhir-1545784504.jpg</li>
<li>lumpia-36 : https://guide.horego.com/wp-content/uploads/2024/02/lumpia-semarang-jpg.webp</li>
<li>lumpia-38 : https://static.cdntap.com/tap-assets-prod/wp-content/uploads/sites/24/2021/07/lumpia-semarang-4.jpg?width=450&quality=90</li>
<li>lumpia-39 : https://www.anekaniaga.id/storage/images/upload/2023/dewi/09-Sep/thumbnail%20artikel%202023122%20-%202023-09-11T134843.031.jpg</li>
<li>lumpia-40 : https://media.gettyimages.com/id/478308009/photo/lumpia-semarang.jpg?s=1024x1024&w=gi&k=20&c=yp3glUlFE3TmBFMok2xpS7bh5c3rNgEdroJI-RkArx4=</li>
<li>lumpia-41 : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSsnZh-NmrcGVWTOgfBRF2eAJ3zZOcUudKyXg&s</li>
<li>lumpia-42 : https://asset-2.tstatic.net/tribunnews/foto/bank/images/resep-lumpia-semarang.jpg</li>
<li>lumpia-43 : https://mmc.tirto.id/image/share/socmed/2021/02/02/istock-1257412486_ratio-16x9.jpg</li>
<li>lumpia-44 : https://s3.bukalapak.com/img/85702662592/s-300-300/Lumpia_Semarang___ukBesar.jpg</li>
<li>lumpia-45 : https://cdn0-production-images-kly.akamaized.net/RxrxT3qv2zah-bf60bui3tM8GOw=/0x21:999x584/800x450/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/4123914/original/020067500_1660535583-shutterstock_2176308357.jpg</li>
<li>lumpia-46 : https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Loenpia_Semarang_with_sauce.JPG/220px-Loenpia_Semarang_with_sauce.JPG</li>
<li>lumpia-47 : https://imgx.sonora.id/crop/0x0:0x0/x/photo/2022/05/24/3-lumpiajpeg-20220524045232.jpeg</li>
<li>lumpia-48 : https://cdn.idntimes.com/content-images/community/2024/10/snapinstaapp-461742346-1179283516698121-351872813867975873-n-1080-66934c0cbeade5e56b2fec27e2f98983-b4a8735768f1dd69cd19f0188b1a09ef_600x400.jpg</li>
<li>lumpia-49 : https://static.promediateknologi.id/crop/0x0:0x0/750x500/webp/photo/p1/420/2024/07/29/Lumpia-semarang-2839732642.jpg</li>
<li>lumpia-50 : https://img-global.cpcdn.com/recipes/3ca3bb0f0f73d5f8/680x482cq70/lumpia-semarang-foto-resep-utama.jpg</li>
<li>lumpia-51 : https://cdn.kmbmedia.id/uploads/2019/01/piyya-piyya.JPG.jpeg?resize=682%2C1024&ssl=1</li>
<li>lumpia-52 : https://media.suara.com/pictures/653x366/2019/06/08/20284-lumpia-semarang.jpg</li>
<li>lumpia-53 : https://static.promediateknologi.id/crop/0x0:0x0/0x0/webp/photo/p2/63/2023/08/10/lumpia-392177800.jpg</li>
<li>lumpia-54 : https://www.dapurkobe.co.id/wp-content/uploads/lumpia-basah-semarang.jpg</li>
<li>lumpia-55 : https://image.popbela.com/content-images/post/20230404/bfb4fc512a7be315632944cdd3f9af2f.jpg?width=1600&format=webp&w=1600</li>
<li>lumpia-56 : https://paxelmarket.co/wp-content/uploads/2021/09/2616042021-llumpiaa.jpg</li>
<li>lumpia-57 : https://foto.kontan.co.id/4Esl_aDZ3LWtPRX7s0WN17B-U0I=/smart/filters:format(webp)/2022/01/31/1167104031p.jpg</li>
<li>lumpia-58 : https://static.promediateknologi.id/crop/3x250:1079x1990/750x500/webp/photo/p1/1005/2023/12/21/Screenshot_20231221_103618_Instagram-3911128352.jpg</li>
<li>lumpia-59 : https://www.masakapahariini.com/wp-content/uploads/2023/01/resep-lumpia-udang-isi-cream-cheese.jpg</li>
<li>lumpia-60 : https://asset.kompas.com/crops/KAAnJeC0ZnUudo-55P8169HOWbs=/0x0:1000x667/1200x800/data/photo/2023/08/12/64d7863019413.jpeg</li>
</ol>

<h3>Other Datasets:</h3>
<br>
<ol>
  <li>Kalori : https://www.fatsecret.co.id/</li>
<ol>

<h3>Import Datasets for Model:</h3>
<br>
<ol>
  <li>Train : https://drive.google.com/drive/folders/1u2u3vknyHSeQlCnvk8QbkIDVuKFJhQVN?usp=drive_link</li>
  <li>Valid : https://drive.google.com/drive/folders/16tc2ZCc-yBuvG0PADhm7KT753KrdT3WQ?usp=drive_link</li>
  <li>Test : https://drive.google.com/drive/folders/1pnuv7FwFK_fbKOy-eopXsZAFcMSP6oIM?usp=drive_link</li>
<ol>
