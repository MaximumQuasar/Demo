import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.BambooOid;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.plan.configuration.ConcurrentBuilds;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.model.task.ScriptTaskProperties;
import com.atlassian.bamboo.specs.util.BambooServer;

@BambooSpec
public class PlanSpec {
    
    public Plan createPlan() {
        final Plan plan = new Plan(new Project()
                .oid(new BambooOid("o8et7z0jo26a"))
                .key(new BambooKey("PRJ"))
                .name("Project Name"),
            "Plan Name",
            new BambooKey("PLANKEY"))
            .oid(new BambooOid("o8540dnbu9s4"))
            .description("Plan created from (enter repository url of your plan)")
            .pluginConfigurations(new ConcurrentBuilds())
            .stages(new Stage("Stage 1")
                    .jobs(new Job("Build and Run",
                            new BambooKey("RUN"))
                            .tasks(new ScriptTask()
                                    .interpreter(ScriptTaskProperties.Interpreter.BINSH_OR_CMDEXE)
                                    .inlineBody("echo ${bamboo.planKey}\n\n${bamboo.capability.system.builder.mvn3.mvn} --version"))))
            .planBranchManagement(new PlanBranchManagement()
                    .delete(new BranchCleanup()))
            .forceStopHungBuilds();
        return plan;
    }
}
