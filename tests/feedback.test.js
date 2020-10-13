const assert=require('assert');
const FeedbackService=require('../services/FeedbackService');

const feedback=new FeedbackService('./data/feedback.json');
describe('Run feedback test',()=>{
    it('feedback.getList()',async()=>{
        const feedbackList=feedback.getList();
        assert.strictEqual(typeof([]),typeof(feedbackList));
    })
})