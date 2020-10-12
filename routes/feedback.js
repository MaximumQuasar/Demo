const express=require("express");

const router=express.Router();

module.exports=(params)=>{
    const {feedbackService} =params;
    const {speakersService}=params;
    router.get('/', async (req,res,next)=>{
        try{
            const feedback=await feedbackService.getList();
            const speakers=await speakersService.getList();
            return res.render("layout",{pageTitle:"feedback",template:"feedback",feedback,speakers});
        }catch(err){
            return next(err);
        }
    });

   router.post('/',(req,res)=>{
        res.send('feedback form submitted');
   });
    return router;
}
