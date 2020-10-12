const express=require("express");
const path=require("path");
const cookieSession=require("cookie-session");
const createError=require('http-errors');

const routes=require('./routes');
const FeedbackService=require("./services/FeedbackService");
const SpeakerService=require("./services/SpeakerService");



const feedbackService=new FeedbackService("./data/feedback.json");
const speakersService=new SpeakerService("./data/speakers.json");

const app=express();
const port=3000;
app.set('view engine','ejs');
app.set('views',path.join(__dirname,"./views"));

app.use(cookieSession({
    name: "session",
    keys:["iam1getme1uknow1yo","howr2asd9849if-cvb0ik"]
}));
app.use(express.static(path.join(__dirname,"./static")));

app.locals.siteName="ROUX Meetups";

app.use(async (req,res,next)=>{
    try{
        const names=await speakersService.getNames();
        res.locals.speakersName=names;
        return next();
    }catch(err){
        return next(err);
    }
})

app.use('/',
    routes({
        feedbackService,
        speakersService
    })
);

app.use((req,res,next)=>{
    return next(createError(404,'File not found'));
})

app.use((err,req,res,next)=>{
    res.locals.message=err.message;
    console.error(err);
    const status=err.status || '500';
    res.locals.status=status;
    res.status(status);
    res.render('error');
})

app.listen(port,()=>{
    // eslint-disable-next-line no-console
    console.log(`Express server listening on port ${port}`);
});

