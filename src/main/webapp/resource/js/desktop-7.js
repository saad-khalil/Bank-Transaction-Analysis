var photo;

function updateName() {
    photo = document.getElementById("file-upload").files[0];
    let a = document.querySelector(".hello-i934166831612-H3Hh4v");
    document.getElementById("test").style.color = "black";
    a.querySelector("div span").innerHTML = photo.name;
}

function valid() {
    const allowedExtensions = ['940'], sizeLimit = 1000000;
    // photo = document.getElementById("file-upload").files[0];
    const {name: filename, size: fileSize} = photo;
    let fs = 0, fn = 0;

    const fileExtension = filename.split(".").pop();

    if (!allowedExtensions.includes(fileExtension)) {

        this.value = null;
        fs = 1


    } else if (fileSize > sizeLimit) {

        fn = 1
    }
    let apple;
    return apple = fn + fs;


}

function onUpload() {
    //console.log(valid());
    if (photo) {
        if (valid() === 0) {

            var reader = new FileReader();
            reader.readAsText(photo, "UTF-8");
            reader.onload = function (evt) {
                console.log(evt.target.result);
            }
            reader.onerror = function (evt) {
                document.getElementById("fileContents").innerHTML = "error reading file";
            }

            let formData = new FormData();

            formData.append("photo", photo);


            fetch('upload', {method: "POST", body: formData}).then(function (response) {

                console.log(response.status)

                if (response.status === 200) {

                    window.location.href = 'data/' + photo.name

                } else {

                    Swal.fire({
                        icon: 'error',
                        title: 'Something went wrong! Try to change the filename!',

                    });
                    ;
                }

            })


        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'You have tried to upload a file in a illegal way but it got intercepted.... ;)  ',
            });

        }
    } else {
        Swal.fire({
            icon: 'error',
            title: 'No file selected',

        });
    }

    console.log(photo);


}

function onDelete() {
    Swal.fire({
        title: 'Do you REALLY want to delete all the files?',
        showDenyButton: true,
        confirmButtonText: `Delete`,
        denyButtonText: `Cancel`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            fetch('upload', {method: "DELETE", body: null}).then(function (response) {
                console.log(response.status)
                if (response.status === 200) {
                    Swal.fire('All files have been successfully deleted!', '', 'success')
                    recent()
                } else {
                    Swal.fire('Something went wrong!', '', 'error')
                }
            })


        } else if (result.isDenied) {
            Swal.fire('DELETE action aborted.', '', 'info')
        }
    })

    // var r = confirm("Do you REALLY want to delete all the files?")
    // if (r == true) {
    //     fetch('upload', {method: "DELETE", body: null}).then(function (response) {
    //         console.log(response.status)
    //         if (response.status === 200) {
    //             alert("All files have been successfully deleted!");
    //             // window.location.href = window.location.href
    //             recent()
    //         } else {
    //             alert("Something went wrong!");
    //         }
    //     })
    // } else {
    //     alert("DELETE action aborted.")
    // }


}


function oneDelete(id) {
    console.log(id);

    var test
    switch (id) {
        case 'x1':
            test = document.querySelector(".hello-i934172831612-ufxz5m").querySelector("div span").innerHTML;
            break;
        case 'x2':
            test = document.querySelector(".rabotran3940-Vb5CER").querySelector("div span").innerHTML;
            break;
        case 'x3':
            test = document.querySelector(".knabtrans-details2022112940-yLCsqY").querySelector("div span").innerHTML;
            break;
    }

    Swal.fire({
        title: 'Do you REALLY want to delete ' + test + ' permanently?',
        showDenyButton: true,
        confirmButtonText: `Delete`,
        denyButtonText: `Cancel`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            // deleteHelp(test)
            fetch('data/' + test, {method: "DELETE"}).then(function (response) {
                console.log(response.status)
                if (response.status == 200) {
                    Swal.fire('The file has been successfully deleted!', '', 'success')
                    recent()

                } else {
                    Swal.fire('Something went wrong!', '', 'error')
                }
            })

        } else if (result.isDenied) {
            Swal.fire('DELETE action aborted.', '', 'info')
        }
    })


    // //var r = confirm("Do you want delete " + test + " permanently?");
    // if (r == true){
    //     deleteHelp(test)
    // }else {
    //     alert("Delete aborted")}
}

// function deleteHelp(test) {
//
//     fetch('data/' + test, {method: "DELETE"}).then(function (response) {
//         console.log(response.status)
//         if (response.status == 200) {
//             Swal.fire('All files have been successfully deleted!', '', 'success')
//             recent()
//         } else {
//             Swal.fire('Something went wrong!', '', 'error')
//         }
//     })
//
// }


// function testingAnimal(test){
//
//     testing().then(async (json) => {
//             dataInfo = json;
//             let banana = await fetch('data/' + test, {method: "DELETE"});
//             if (!banana.ok){
//                 throw new Error('Error YEET');
//             }
//
//         }
//
//
//     )
//
// }

async function testing() {

    let appleSeed = await fetch('upload', {headers: {'Accept': 'application/json'}})
    if (!appleSeed.ok) {
        throw new Error('HTTP error : $ (appleSeed.status)');
    }
    return await appleSeed.json();

}


var dataInfo

function recent() {

    testing().then((json) => {

            dataInfo = json;
            console.log(dataInfo)


            let t1 = document.querySelector('.frame-5-JuX2NU');
            let t2 = document.querySelector('.frame-5-Kc7dwT');
            let t3 = document.querySelector('.frame-5-NWpetK');
            let t4 = document.getElementById("x1")
            let t5 = document.getElementById("x2")
            let t6 = document.getElementById("x3")

            if (dataInfo.length === 0) {

                document.querySelector('.label-text-style-i934172644-JuX2NU').innerHTML = "NO RECENT FILES"

                if (t1) {
                    t1.remove();
                    if (t4.hasChildNodes()) {
                        for (let i = t4.childNodes.length; i > 0; i--) {
                            t4.removeChild(t4.childNodes[0])
                        }
                    }
                }

                if (t2) {
                    t2.remove();
                    if (t5.hasChildNodes()) {
                        for (let i = t5.childNodes.length; i > 0; i--) {
                            t5.removeChild(t5.childNodes[0])
                        }
                    }
                }


                if (t3) {
                    t3.remove();
                    if (t6.hasChildNodes()) {
                        for (let i = t6.childNodes.length; i > 0; i--) {
                            t6.removeChild(t6.childNodes[0])
                        }
                    }
                }


            } else if (dataInfo.length === 1) {

                document.querySelector('.label-text-style-i934172644-JuX2NU').innerHTML = "RECENT FILES ( " + 1 + " of " + dataInfo.length + " )";

                if (t3) {
                    t3.remove();
                    if (t6.hasChildNodes()) {
                        for (let i = t6.childNodes.length; i > 0; i--) {
                            t6.removeChild(t6.childNodes[0])
                        }
                    }
                }
                if (t2) {
                    t2.remove();
                    if (t5.hasChildNodes()) {
                        for (let i = t5.childNodes.length; i > 0; i--) {
                            t5.removeChild(t5.childNodes[0])
                        }
                    }
                }
                if (t1 && t1.innerText === dataInfo[0]) {
                } else {
                    if (t1) {
                        t1.remove();
                        if (t4.hasChildNodes()) {
                            for (let i = t4.childNodes.length; i > 0; i--) {
                                t4.removeChild(t4.childNodes[0])
                            }
                        }
                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-JuX2NU">
                               <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">
                                  <button id="recent1" hidden onclick="recentSearch('one')"></button>
                                    <label class="clickable" for="recent1"><span></span></label>
                            </div>
                           </div>`)
                        document.querySelector('#x1').insertAdjacentHTML('afterbegin', `<img id="x11" class="line-7-anPZFN" src="../resource/img/line-7@2x.svg"/>
                <img id="x12" class="line-8-anPZFN" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".hello-i934172831612-ufxz5m");
                        test.querySelector("div span").innerHTML = dataInfo[0]
                    } else {
                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-JuX2NU">
                               <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">
                                  <button id="recent1" hidden onclick="recentSearch('one')"></button>
                                    <label class="clickable" for="recent1"><span></span></label>
                            </div>
                           </div>`)
                        document.querySelector('#x1').insertAdjacentHTML('afterbegin', `<img id="x11" class="line-7-anPZFN" src="../resource/img/line-7@2x.svg"/>
                <img id="x12" class="line-8-anPZFN" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".hello-i934172831612-ufxz5m");
                        test.querySelector("div span").innerHTML = dataInfo[0]
                    }
                }
            } else if (dataInfo.length === 2) {

                document.querySelector('.label-text-style-i934172644-JuX2NU').innerHTML = "RECENT FILES ( " + 2 + " of " + dataInfo.length + " )";
                if (t3) {
                    t3.remove();
                    if (t6.hasChildNodes()) {
                        for (let i = t6.childNodes.length; i > 0; i--) {
                            t6.removeChild(t6.childNodes[0])
                        }
                    }
                }

                if (t1 && t1.innerText === dataInfo[0]) {
                } else {
                    if (t1) {
                        t1.remove();
                        if (t4.hasChildNodes()) {
                            for (let i = t4.childNodes.length; i > 0; i--) {
                                t4.removeChild(t4.childNodes[0])
                            }

                            document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-JuX2NU">
                               <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">
                                  <button id="recent1" hidden onclick="recentSearch('one')"></button>
                                    <label class="clickable" for="recent1"><span></span></label>
                            </div>
                           </div>`)
                            document.querySelector('#x1').insertAdjacentHTML('afterbegin', `<img id="x11" class="line-7-anPZFN" src="../resource/img/line-7@2x.svg"/>
                <img id="x12" class="line-8-anPZFN" src="../resource/img/line-8@2x.svg"/>`)
                            let test = document.querySelector(".hello-i934172831612-ufxz5m");
                            test.querySelector("div span").innerHTML = dataInfo[0]
                        }
                    } else {


                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-JuX2NU">
                               <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">
                                  <button id="recent1" hidden onclick="recentSearch('one')"></button>
                                    <label class="clickable" for="recent1"><span></span></label>
                            </div>
                           </div>`)
                        document.querySelector('#x1').insertAdjacentHTML('afterbegin', `<img id="x11" class="line-7-anPZFN" src="../resource/img/line-7@2x.svg"/>
                <img id="x12" class="line-8-anPZFN" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".hello-i934172831612-ufxz5m");
                        test.querySelector("div span").innerHTML = dataInfo[0]
                    }
                }
                if (t2 && t2.innerText === dataInfo[1]) {
                } else {
                    if (t2) {
                        t2.remove();
                        if (t5.hasChildNodes()) {
                            for (let i = t5.childNodes.length; i > 0; i--) {
                                t5.removeChild(t5.childNodes[0])
                            }
                            document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-Kc7dwT">
                            <div class="rabotran3940-Vb5CER valign-text-middle roboto-normal-black-24px">
                                <button id="recent2" hidden onclick="recentSearch('two')"></button>
                                <label class="clickable" for="recent2"><span></span></label>
                            </div>
                        </div>`)
                            document.querySelector('#x2').insertAdjacentHTML('afterbegin', ` <img id="x21" class="line-7-oPFQG7" src="../resource/img/line-7@2x.svg"/>
                        <img  id="x22" class="line-8-oPFQG7" src="../resource/img/line-8@2x.svg"/>`)
                            let test = document.querySelector(".rabotran3940-Vb5CER");
                            test.querySelector("div span").innerHTML = dataInfo[1]
                        }
                    } else {

                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-Kc7dwT">
                            <div class="rabotran3940-Vb5CER valign-text-middle roboto-normal-black-24px">
                                <button id="recent2" hidden onclick="recentSearch('two')"></button>
                                <label class="clickable" for="recent2"><span></span></label>
                            </div>
                        </div>`)
                        document.querySelector('#x2').insertAdjacentHTML('afterbegin', ` <img id="x21" class="line-7-oPFQG7" src="../resource/img/line-7@2x.svg"/>
                        <img  id="x22" class="line-8-oPFQG7" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".rabotran3940-Vb5CER");
                        test.querySelector("div span").innerHTML = dataInfo[1]
                    }
                }


            } else if (dataInfo.length >= 3) {

                document.querySelector('.label-text-style-i934172644-JuX2NU').innerHTML = "RECENT FILES (  " + 3 + " of " + dataInfo.length + " )";

                if (t1 && t1.innerText === dataInfo[0]) {
                } else {
                    if (t1) {
                        t1.remove();
                        if (t4.hasChildNodes()) {
                            for (let i = t4.childNodes.length; i > 0; i--) {
                                t4.removeChild(t4.childNodes[0])
                            }
                            document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-JuX2NU">
                               <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">
                                  <button id="recent1" hidden onclick="recentSearch('one')"></button>
                                    <label class="clickable" for="recent1"><span></span></label>
                            </div>
                           </div>`)
                            document.querySelector('#x1').insertAdjacentHTML('afterbegin', `<img id="x11" class="line-7-anPZFN" src="../resource/img/line-7@2x.svg"/>
                <img id="x12" class="line-8-anPZFN" src="../resource/img/line-8@2x.svg"/>`)
                            let test = document.querySelector(".hello-i934172831612-ufxz5m");
                            test.querySelector("div span").innerHTML = dataInfo[0]
                        }
                    } else {
                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-JuX2NU">
                               <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">
                                  <button id="recent1" hidden onclick="recentSearch('one')"></button>
                                    <label class="clickable" for="recent1"><span></span></label>
                            </div>
                           </div>`)
                        document.querySelector('#x1').insertAdjacentHTML('afterbegin', `<img id="x11" class="line-7-anPZFN" src="../resource/img/line-7@2x.svg"/>
                <img id="x12" class="line-8-anPZFN" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".hello-i934172831612-ufxz5m");
                        test.querySelector("div span").innerHTML = dataInfo[0]
                    }
                }

                if (t2 && t2.innerText === dataInfo[1]) {
                } else {
                    if (t2) {
                        t2.remove();
                        if (t5.hasChildNodes()) {
                            for (let i = t5.childNodes.length; i > 0; i--) {
                                t5.removeChild(t5.childNodes[0])
                            }
                        }
                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-Kc7dwT">
                            <div class="rabotran3940-Vb5CER valign-text-middle roboto-normal-black-24px">
                                <button id="recent2" hidden onclick="recentSearch('two')"></button>
                                <label class="clickable" for="recent2"><span></span></label>
                            </div>
                        </div>`)
                        document.querySelector('#x2').insertAdjacentHTML('afterbegin', ` <img id="x21" class="line-7-oPFQG7" src="../resource/img/line-7@2x.svg"/>
                        <img  id="x22" class="line-8-oPFQG7" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".rabotran3940-Vb5CER");
                        test.querySelector("div span").innerHTML = dataInfo[1]
                    } else {

                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-Kc7dwT">
                            <div class="rabotran3940-Vb5CER valign-text-middle roboto-normal-black-24px">
                                <button id="recent2" hidden onclick="recentSearch('two')"></button>
                                <label class="clickable" for="recent2"><span></span></label>
                            </div>
                        </div>`)
                        document.querySelector('#x2').insertAdjacentHTML('afterbegin', ` <img id="x21" class="line-7-oPFQG7" src="../resource/img/line-7@2x.svg"/>
                        <img  id="x22" class="line-8-oPFQG7" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".rabotran3940-Vb5CER");
                        test.querySelector("div span").innerHTML = dataInfo[1]
                    }
                }

                if (t3 && t3.innerText === dataInfo[2]) {
                } else {
                    if (t3) {
                        t3.remove();
                        if (t6.hasChildNodes()) {
                            for (let i = t6.childNodes.length; i > 0; i--) {
                                t6.removeChild(t6.childNodes[0])
                            }
                        }
                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-NWpetK">
                            <div class="knabtrans-details2022112940-yLCsqY valign-text-middle roboto-normal-black-24px">
                                <button id="recent3" hidden onclick="recentSearch('three')"></button>
                                <label class="clickable" for="recent3"><span></span></label>
                            </div>
                        </div>`)
                        document.querySelector('#x3').insertAdjacentHTML('afterbegin', ` <img id="x31" class="line-7-8uBQKT" src="../resource/img/line-7@2x.svg"/>
                        <img id="x32" class="line-8-8uBQKT" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".knabtrans-details2022112940-yLCsqY");
                        test.querySelector("div span").innerHTML = dataInfo[2]

                    } else {


                        document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-NWpetK">
                            <div class="knabtrans-details2022112940-yLCsqY valign-text-middle roboto-normal-black-24px">
                                <button id="recent3" hidden onclick="recentSearch('three')"></button>
                                <label class="clickable" for="recent3"><span></span></label>
                            </div>
                        </div>`)
                        document.querySelector('#x3').insertAdjacentHTML('afterbegin', ` <img id="x31" class="line-7-8uBQKT" src="../resource/img/line-7@2x.svg"/>
                        <img id="x32" class="line-8-8uBQKT" src="../resource/img/line-8@2x.svg"/>`)
                        let test = document.querySelector(".knabtrans-details2022112940-yLCsqY");
                        test.querySelector("div span").innerHTML = dataInfo[2]
                    }
                }


            }


        }
    ).catch(e => console.log(e))

}

// function recent() {
//
//         testing().then((json) => {
//
//                 dataInfo = json;
//                 console.log(dataInfo)
//
//             if(dataInfo[0] == null){
//                 document.querySelector('.label-text-style-i934172644-JuX2NU').innerHTML = "NO RECENT FILES"
//                 let t1 = document.querySelector('.frame-5-JuX2NU')
//                 if (t1){
//                     t1.remove();
//                 }
//                 let t2 = document.querySelector('.frame-5-Kc7dwT')
//                 if (t2){
//                     t2.remove();
//                 }
//                 let t3 = document.querySelector('.frame-5-NWpetK')
//                 if (t3){
//                     t3.remove();
//                 }
//                 let t4 = document.getElementById("x1")
//                 if (t4){
//                     t4.remove();
//                 }
//                 let t5 = document.getElementById("x2")
//                 if(t5){
//                     t5.remove();
//                 }
//                 let t6 = document.getElementById("x3")
//                 if (t6){
//                     t6.remove();
//                 }
//             }
//             else {
//                 let t1 = document.querySelector('.frame-5-JuX2NU')
//                 if (t1){
//                     t1.remove();
//                 }
//                 let t4 = document.getElementById("x11")
//                 if (t4){
//                     t4.remove();
//                 }
//                 let t6 = document.getElementById("x12")
//                 if (t6){
//                     t6.remove();
//                 }
//                 document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-JuX2NU">
//                                <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">
//                                   <button id="recent1" hidden onclick="recentSearch('one')"></button>
//                                     <label class="clickable" for="recent1"><span></span></label>
//                             </div>
//                            </div>`)
//                 document.querySelector('#x1').insertAdjacentHTML('afterbegin',`<img id="x11" class="line-7-anPZFN" src="../resource/img/line-7@2x.svg"/>
//                 <img id="x12" class="line-8-anPZFN" src="../resource/img/line-8@2x.svg"/>`)
//                 let test = document.querySelector(".hello-i934172831612-ufxz5m");
//                 test.querySelector("div span").innerHTML = dataInfo[0]
//             }
//
//             if(dataInfo[1] == null){
//                 let t2 = document.querySelector('.frame-5-Kc7dwT')
//                 if (t2){
//                     t2.remove();
//                 }
//                 let t3 = document.querySelector('.frame-5-NWpetK')
//                 if (t3){
//                     t3.remove();
//                 }
//                 let t5 = document.getElementById("x2")
//                 if(t5){
//                     t5.remove();
//                 }
//                 let t6 = document.getElementById("x3")
//                 if (t6){
//                     t6.remove();
//                 }
//             }
//             else {
//                 let t1 = document.querySelector('.frame-5-Kc7dwT')
//                 if (t1){
//                     t1.remove();
//                 }
//
//                 document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-Kc7dwT">
//                             <div class="rabotran3940-Vb5CER valign-text-middle roboto-normal-black-24px">
//                                 <button id="recent2" hidden onclick="recentSearch('two')"></button>
//                                 <label class="clickable" for="recent2"><span></span></label>
//                             </div>
//                         </div>`)
//                 document.querySelector('#x2').insertAdjacentHTML('afterbegin',` <img id="x21" class="line-7-oPFQG7" src="../resource/img/line-7@2x.svg"/>
//                 <img  id="x22" class="line-8-oPFQG7" src="../resource/img/line-8@2x.svg"/>`)
//                 let test = document.querySelector(".rabotran3940-Vb5CER");
//                 test.querySelector("div span").innerHTML = dataInfo[1]
//
//             }
//
//             if(dataInfo[2] == null){
//                 let t3 = document.querySelector('.frame-5-NWpetK')
//                 if (t3){
//                     t3.remove();
//                 }
//                 let t6 = document.getElementById("x3")
//                 if (t6){
//                     t6.remove();
//                 }
//
//             }
//             else {
//                 document.querySelector('#recentOne').insertAdjacentHTML('beforeend', `<div class="frame-5-NWpetK">
//                             <div class="knabtrans-details2022112940-yLCsqY valign-text-middle roboto-normal-black-24px">
//                                 <button id="recent3" hidden onclick="recentSearch('three')"></button>
//                                 <label class="clickable" for="recent3"><span></span></label>
//                             </div>
//                         </div>`)
//                 document.querySelector('#x3').insertAdjacentHTML('afterbegin',` <img id="x31" class="line-7-8uBQKT" src="../resource/img/line-7@2x.svg"/>
//                 <img id="x32" class="line-8-8uBQKT" src="../resource/img/line-8@2x.svg"/>`)
//                 let test = document.querySelector(".knabtrans-details2022112940-yLCsqY");
//                 test.querySelector("div span").innerHTML = dataInfo[2]
//             }
//
//             }
//         ).catch(e  => console.log(e))
//
//
//
//
// }


function recentSearch(id) {
    console.log(id);


    switch (id) {

        case "one":
            let one = document.querySelector(".hello-i934172831612-ufxz5m").querySelector("div span").innerHTML
            recentHelp(one);
            break;
        case "two":
            let two = document.querySelector(".rabotran3940-Vb5CER").querySelector("div span").innerHTML
            recentHelp(two);
            break;
        case "three":
            let three = document.querySelector(".knabtrans-details2022112940-yLCsqY").querySelector("div span").innerHTML
            recentHelp(three);
            break;

    }


    // console.log(b);
    // const params = { filename: b };
    // const urlParams = new URLSearchParams(Object.entries(params));
    // //fetch('data?' + urlParams).then(response => response.json()).then(data => console.log(data));
    // window.location = "data" + "?filename=" + escape(b);
}

function recentHelp(b) {
    console.log("data/" + escape(b));
    //const params = { filename: b };
    //const urlParams = new URLSearchParams(Object.entries(params));
    //fetch('data?' + urlParams).then(response => response.json()).then(data => console.log(data));
    window.location = "data/" + escape(b);

}

function onLogout() {
    window.location.href = "/BTA/"
    let cookies = document.cookie.split(";");

    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i];
        let eqPos = cookie.indexOf("=");
        let name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}