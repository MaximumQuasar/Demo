const express=require("express");
const speakerRoute=require("./speakers");
const feedbackRoute=require("./feedback");

const router=express.Router();

module.exports=(params)=>{
    const {speakersService}=params;
    router.get('/',async (req,res,next)=>{
        try{
            const speakersList=await speakersService.getList();
            const artworks=await speakersService.getAllArtwork();
            return res.render('layout',{pageTitle:'Welcome', template: "index",speakersList,artworks});
        }
        catch(err){
            return next(err);
        }
    });
   
    router.use('/speakers',speakerRoute(params));
    router.use('/feedback',feedbackRoute(params));
    return router;
}
