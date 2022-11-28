<template>
    <v-container id="print">
        <body>
        <div class="dynamic-content">
            <v-container
                class="d-flex justify-center align-center"
            >
                <h1 id="display_name">{{ className }}</h1>

                <h4> {{ memberName}} </h4>
            </v-container>
        </div>
        </body>
    </v-container>
</template>

<script src="https://cdnjs.cloudflare.com/ajax/libs/dom-to-image/2.6.0/dom-to-image.js"></script>
<script>

export default {
    data() {
        return {
            className: '',
            classType: '',
            startDate: '',
            endDate: '',
            memberName: '',
            birth: '',
        }
    },
    created() {
        this.info = this.$route.query.info;
        this.className = this.$route.query.className;
        this.classType = this.$route.query.classType;
        this.startDate = this.$route.query.startDate;
        this.endDate = this.$route.query.endDate;
        this.memberName = this.$route.query.memberName;
        this.birth = this.$route.query.birth;

        window.onload = function() {
            let initBody = document.body.innerHTML;
            document.body.innerHTML = document.getElementById('print').innerHTML;

            setTimeout(function() { window.print();}, 250);

            window.onafterprint = function() { window.close();}
        }
    },
}
</script>

<style scoped>
*{
    -webkit-print-color-adjust: exact !important;
    color-adjust: exact !important; 
    print-color-adjust: exact !important;
}

.dynamic-content{
    text-align: left;
}

img{
  max-width: 950px;
  max-height: 721px;
  align-content: center;
  
}

h1{
    color: #E1BA61;
    padding-top: 220px;
}

h4 {
    color: #E1BA61;
    padding-top: 50px;
}

div{
  display:table;
  white-space: nowrap;
  text-align: center;
  width: 950px;
  height: 721px; 
  background-image: url("https://purepng.com/public/uploads/large/purepng.com-certificate-templateobjectscertificate-templateobject-award-certificate-template-631522323360ryv35.png");
  background-repeat: no-repeat;
  background-size: 950px 721px;
/*   background-image: url("");  */
}


</style>