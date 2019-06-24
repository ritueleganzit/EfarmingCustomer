package com.eleganzit.e_farmingcustomer;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;

import com.eleganzit.e_farmingcustomer.databinding.MainActivityBinding;


public class MainActivity extends AppCompatActivity implements View.OnDragListener {

    private MainActivityBinding mainActivityBinding;
    public ObservableArrayList<ExcercisePojo> exerciseList;
    public ObservableArrayList<ExcercisePojo> exerciseSelectedList = new ObservableArrayList<>();
    public ExcercisePojo exerciseToMove;
    private int newContactPosition = -1;

    private int currentPosition = -1;
    private boolean isExerciseAdded = false;
    public static boolean isFromExercise = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        loadExerciseData();
        mainActivityBinding.setMainActivity(this);
        mainActivityBinding.rcvSelectedExercise.setOnDragListener(this);


        mainActivityBinding.rcvChooseExercise.setOnDragListener(new MyDragInsideRcvListener());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.scale_3dp);
        mainActivityBinding.rcvSelectedExercise.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        mainActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void loadExerciseData() {
        exerciseList = new ObservableArrayList<>();

        exerciseList.add(new ExcercisePojo(1, "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo(2, "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo(3, "Vegetable " + 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));
        exerciseList.add(new ExcercisePojo(4, "Vegetable " + 4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseList.add(new ExcercisePojo(5, "Vegetable " + 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
        exerciseList.add(new ExcercisePojo(6, "Vegetable " + 6, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo(7, "Vegetable " + 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
        exerciseList.add(new ExcercisePojo(8, "Vegetable " + 8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseList.add(new ExcercisePojo(9, "Vegetable " + 9, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo(10, "Vegetable " + 10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));

        if(exerciseList.size()==0)
        {
            mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
        }
        else
        {
            mainActivityBinding.noVeg.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        View selectedView = (View) dragEvent.getLocalState();
        RecyclerView rcvSelected = (RecyclerView) view;

        try {
            int currentPosition = mainActivityBinding.rcvChooseExercise.getChildAdapterPosition(selectedView);

            // Ensure the position is valid.
            if (currentPosition != -1) {
                exerciseToMove = exerciseList.get(currentPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                View onTopOf = rcvSelected.findChildViewUnder(dragEvent.getX(), dragEvent.getY());
                newContactPosition = rcvSelected.getChildAdapterPosition(onTopOf);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
                break;
            case DragEvent.ACTION_DROP:
                //when Item is dropped off to recyclerview.
                if (isFromExercise) {
                    exerciseSelectedList.add(exerciseToMove);
                    exerciseList.remove(exerciseToMove);
                    if(exerciseList.size()==0)
                    {
                        mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mainActivityBinding.noVeg.setVisibility(View.GONE);
                    }
                    mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemRemoved(currentPosition);
                    mainActivityBinding.executePendingBindings();
                }
                //This is to hide/add the container!
                /*ViewGroup owner = (ViewGroup) (view.getParent());
                if (owner != null) {
                    //owner.removeView(selectedView);
                    //owner.addView(selectedView);

                    try {
                        LinearLayout rlContainer = (LinearLayout) view;
                        rlContainer.addView(selectedView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //selectedView.setVisibility(View.VISIBLE);
                }*/

                break;

            case DragEvent.ACTION_DRAG_ENDED:
                // Reset the visibility for the Contact item's view.
                // This is done to reset the state in instances where the drag action didn't do anything.
                selectedView.setVisibility(View.VISIBLE);
                // Boundary condition, scroll to top is moving list item to position 0.
                if (newContactPosition != -1) {
                    rcvSelected.scrollToPosition(newContactPosition);
                    newContactPosition = -1;
                } else {
                    rcvSelected.scrollToPosition(0);
                }
                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
            default:
                break;
        }
        return true;
    }

    /**
     * This listener class is for Vertical Recyclerview.
     */
    class MyDragInsideRcvListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            RecyclerView rcv = (RecyclerView) v;

            View selectedView = (View) event.getLocalState();
            try {
                int currentPosition = rcv.getChildAdapterPosition(selectedView);
                // Ensure the position is valid.
                if (currentPosition != -1) {
                    exerciseToMove = exerciseSelectedList.get(currentPosition);
                    //exerciseSelectedList.get(currentPosition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    View onTopOf = rcv.findChildViewUnder(event.getX(), event.getY());
                    newContactPosition = rcv.getChildAdapterPosition(onTopOf);

                    //Flag for our own understanding!
                    //isFromExercise = true;

                    //This is for internal dragging (inside recyclerview only).  VVIP!
                    // Ensure the new position is valid.

                    //If you wanted to swap the items in recyclerview only.
                    //It requires bit changes.
                   /* if (newContactPosition != -1) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) rcv.getLayoutManager();
                        int firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                        // Scroll up or down one if we are over the first or last visible list item.
                        if (newContactPosition >= lastVisiblePosition)
                            layoutManager.scrollToPositionWithOffset(firstVisiblePosition + 1, 0);
                        else if (newContactPosition <= firstVisiblePosition)
                            layoutManager.scrollToPositionWithOffset(firstVisiblePosition - 1, 0);

                        // Update the location of the Contact
                        exerciseList.remove(exerciseToMove);
                        exerciseList.add(newContactPosition, exerciseToMove);
                        rcv.getAdapter().notifyDataSetChanged();
                    }*/
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Reset the visibility for the Contact item's view.
                    // This is done to reset the state in instances where the drag action didn't do anything.
                    selectedView.setVisibility(View.VISIBLE);
                    // Boundary condition, scroll to top is moving list item to position 0.
                    if (newContactPosition != -1) {
                        rcv.scrollToPosition(newContactPosition);
                        newContactPosition = -1;
                    } else {
                        rcv.scrollToPosition(0);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    if (!isFromExercise) {
                        //THIS IS FOR WHEN WE TAKE ITEM OF OTHER LIST AND DROP IN THIS LIST.
                        exerciseList.add(exerciseToMove);
                        exerciseSelectedList.remove(exerciseToMove);
                        mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemInserted(currentPosition);
                        mainActivityBinding.executePendingBindings();
                    }
                    break;

                default:
                    break;
            }
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

