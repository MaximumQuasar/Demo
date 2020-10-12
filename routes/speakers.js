const express=require("express");

const router=express.Router();

module.exports=(params)=>{
    const {speakersService}=params;
    router.get('/',async (req,res,next)=>{
        try{
            const speakers=await speakersService.getList();
            const artworks=await speakersService.getAllArtwork();
            return res.render("layout",{pageTitle:"Speakers",template:"speakers",speakers,artworks});
        }catch(err){
            return next(err);
        }
    });

   router.get('/:speakname',async (req,res,next)=>{
       try{
            const speaker=await speakersService.getSpeaker(req.params.speakname);
            const artworks=await speakersService.getArtworkForSpeaker(req.params.speakname);
            return res.render("layout",{pageTitle:"Speaker",template:"speakerDetails",speaker,artworks})
       } catch(err){
            return next(err);
       }
   });
    return router;
}
